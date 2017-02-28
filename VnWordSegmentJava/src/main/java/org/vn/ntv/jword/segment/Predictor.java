/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vn.ntv.jword.segment;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author namtv19
 */
public class Predictor implements Closeable {

    static {
        OSValidator.print();
        if (OSValidator.isUnix()) {
            System.load(new File("/home/namtv19/NetBeansProjects/VnWordSegment/dist/Debug/GNU-Linux-x86/vnwordsegment.so").getAbsolutePath()); // Load native library at runtime
        } else {
            System.load(new File("native/vnwordsegment.dll").getAbsolutePath()); // Load native library at runtime
        }
        // hello.dll (Windows) or libhello.so (Unixes)
    }

    private long handle = 0;

    public Predictor() {

    }

    public Predictor init(int window, String modelPath) {
        handle = createNativeObject(window, modelPath);
        return this;
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            if (handle != 0) {
                deleteNativeObject(handle);
            }
        }

    }

    /**
     *
     * @param in
     * @return
     */
    public String segment(String in) {
        if (handle != 0) {
            return segment(in, handle);
        } else {
            return in;
        }
    }

    /**
     * dongdu learn -i corpus_link -o model&map_folder_path -w window_length
     * dongdu predict -i input_path -o output_path
     *
     * @param argc
     * @param args
     */
    public void executeCmd(int argc, String[] args) {
        if (handle != 0) {
            execute(argc, args);
        }
    }

    // Declare a native method sayHello() that receives nothing and returns void
    private static native long createNativeObject(int window, String modelPath);

    private static native void deleteNativeObject(long handle);

    private static native void printObjectDetails(long handle);

    private static native String segment(String input, long handle);

    private static native void execute(int argc, String[] args);

    public static void main(String[] args) throws IOException {
        Predictor ob = new Predictor().init(3, "./data/");
        String s ;
//        s= ob.segment("Các trọng tài làm nhiệm vụ ở các giải bóng đá chuyên nghiệp Việt Nam những năm gần đây đều do Ban trọng tài VFF phân công");
//        System.out.println(s);
//        s = ob.segment("Cụ thể, bắt đầu từ vòng chín V-League, các trọng tài làm nhiệm vụ phải được sự thống nhất của bốn thành viên. Cụ thể là trưởng Ban tổ chức giải Nguyễn Minh Ngọc, Tổng Giám đốc VPF Cao Văn Chóng, thành viên Hội đồng quản trị VPF Phạm Ngọc Viễn và Trưởng hoặc phó ban trọng tài.");
//        System.out.println(s);
//       s = ob.segment("“Chúng tôi ra quy chế này và bắt đầu thực hiện từ vòng đấu thứ chín”, ông Nguyễn Quốc Thắng, Chủ tịch VPF, cho biết. “Danh sách các trọng tài làm nhiệm vụ sẽ do Ban trọng tài gửi lên. Một trọng tài được phân công làm nhiệm vụ phải nhận được sự đồng ý của ít nhất ba trong số bốn thành viên kể trên. Còn lại, nếu làm không tốt, chúng tôi không thuê nữa”." +
//"" +
//"Từ vòng tám, việc phân công trọng tài vẫn do Ban trọng tài quyết định. Tuy nhiên, những trọng tài mắc nhiều sai sót từ đầu giải sẽ không được làm nhiệm vụ." +
//""+
//"Ngoài ra, một điểm mới ở việc điều chỉnh lần này là nếu phát hiện trọng tài có vấn đề và không phù hợp, tổ chức trên có thể sẽ thay trọng tài trước giờ bóng lăn một đến hai tiếng."
//       );
//        System.out.println(s);
        String[] argss = "learn -i /home/namtv19/MachineLearning/DongDu-master/copurs -o /home/namtv19/NetBeansProjects/VnWordSegment/out/ -w 3".split(" ");
        ob.executeCmd(argss.length, argss);
        ob.close();
    }

}
