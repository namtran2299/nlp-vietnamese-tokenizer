#include <jni.h>
#include <string>
#include "PredictorJNI.h"
#include "Machine.h"
#include "predictor.h"
#include "learner.h"

using namespace std;

/*
 * Class:     org_vn_ntv_jword_segment_Predictor
 * Method:    createNativeObject
 * Signature: (ILjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_org_vn_ntv_jword_segment_Predictor_createNativeObject
(JNIEnv *env, jclass obj, jint window, jstring path) {
    //    printf("\nJava_org_vn_ntv_jword_segment_Predictor_createNativeObject\n");
    const char *pathModel = env->GetStringUTFChars(path, NULL);
    std::string dir(pathModel);
    ensureEndingSlash(dir);
    Machine* predictor = new Machine(window, dir, PREDICT);
    env->ReleaseStringUTFChars(path, pathModel); // release resources

    if (!predictor->load()) {
        cout << "Failed to load data from dongdu.model and dongdu.map" << endl;
        delete predictor;
        return 0;
    }

    return (long) (predictor);
}

/*
 * Class:     org_vn_ntv_jword_segment_Predictor
 * Method:    deleteNativeObject
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_vn_ntv_jword_segment_Predictor_deleteNativeObject
(JNIEnv *env, jclass, jlong handle) {
    //    printf("\nJava_org_vn_ntv_jword_segment_Predictor_deleteNativeObject\n");
    Machine* predictor = (Machine*) (handle);
    delete predictor;
}

/*
 * Class:     org_vn_ntv_jword_segment_Predictor
 * Method:    printObjectDetails
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_org_vn_ntv_jword_segment_Predictor_printObjectDetails
(JNIEnv *, jclass, jlong) {
    printf("\nJava_org_vn_ntv_jword_segment_Predictor_printObjectDetails\n");
}

/*
 * Class:     org_vn_ntv_jword_segment_Predictor
 * Method:    segment
 * Signature: (Ljava/lang/String;J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_vn_ntv_jword_segment_Predictor_segment
(JNIEnv *env, jclass, jstring input, jlong handle) {
    //    printf("\nJava_org_vn_ntv_jword_segment_Predictor_segment\n");
    const char *inC = env->GetStringUTFChars(input, NULL);
    Machine* predictor = (Machine*) ((long) (handle));
    string inS(inC);
    env->ReleaseStringUTFChars(input, inC); // release resources
    string s = predictor->segment(inS);
        printf(s.c_str());
    jstring jRet = env->NewStringUTF(s.c_str());

    return jRet;
}

/*
 * Class:     org_vn_ntv_jword_segment_Predictor
 * Method:    execute
 * Signature: (I[Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_vn_ntv_jword_segment_Predictor_execute
(JNIEnv *env, jclass, jint window, jobjectArray inJNIArray) {
    //    printf("\nJava_org_vn_ntv_jword_segment_Predictor_execute\n");
    // Get the value of each Integer object in the array
    jsize length = env->GetArrayLength(inJNIArray);
    jint sum = 0;
    int i;
    char** items = MyMalloc(char*, sizeof (char*) * length);

    for (i = 0; i < length; i++) {
        jstring item = (jstring) env->GetObjectArrayElement(inJNIArray, i);
        if (NULL == item) {
            return;
        }
        const char *inC = env->GetStringUTFChars(item, NULL);
        jsize l = env->GetStringUTFLength(item);
        char* text = MyMalloc(char, sizeof (char) * (l+1));
        memcpy(text, inC, l);
        text[l]=0;
        items[i] = text;
        env->ReleaseStringUTFChars(item, inC); // release resources
    }
    printf(items[0]);printf("\n");
    if (length == 0) {
        printf("No Param!");
        learner_usage();
        printf("\n");
        predictor_usage();
        return;
    }
    if (strcmp(items[0], "predict") == 0) {
        predictor_main(length, items);
    } else if (strcmp(items[0], "learn") == 0) {
        learner_main(length, items);
    } else {
        printf("No Mode!");
        learner_usage();
        printf("\n");
        predictor_usage();
    }
    for (i = 0; i < length; i++) {
        free(items[i]);
    }
    free(items);
}