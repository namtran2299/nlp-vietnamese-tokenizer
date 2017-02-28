#ifndef MACHINE_H_
#define MACHINE_H_

#include "Feats.h"
#include "DicMap.h"
#include "StrMap.h"
#include "SylMap.h"
#include "configure.h"
#include "./liblinear/linear.h"

#include <string>
#include <time.h>
#include <ctime>

namespace std {

class Machine {
private:
	size_t index_SPACE;
	size_t index_UNDER;
	size_t reference;
	DicMap		dicmap;
	StrMap		strmap;
        
        int  WINDOW_LENGTH;
	string PATH;
        
	model*		_model;
        
//	problem   _problem;
//	Feats* 		feats;
//	vector<featuresOfSyllabel>* vfeats;
	

	size_t getByteOfUTF8(unsigned char c);
	string itostr(int x);
	vector<featuresOfSyllabel>* convert(string sentence,Feats*  feats);

public:
	Machine(int window_length, string path, StrMapReference ref);
	virtual ~Machine();

	vector<featuresOfSyllabel>* extractTest(string sentence, StrMapReference ref);
        vector<featuresOfSyllabel>* extract(string sentence, StrMapReference ref,Feats*  feats);
	problem getProblem(Feats*  feats);
	void delProblem(problem& _problem);
	/* Learner */
	problem training(Feats*  feats);
	void print();
	double close_test(Feats* feats, problem& _problem);
	void featuresSelection();

	/* Predictor */
	bool load();
	string segment(string sentence);
};

} /* namespace std */
#endif /* Machine_H_ */
