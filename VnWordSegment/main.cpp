/* 
 * File:   main.cpp
 * Author: namtv19
 *
 * Created on February 26, 2017, 10:41 PM
 */

#include <cstdlib>
#include <string.h>
#include <stdio.h>
#include "predictor.h"
#include "learner.h"

using namespace std;

/*
 * 
 */
int main1(int argc, char** argv) {
    if(argc==0){
        printf("No Param!");
        learner_usage();
        predictor_usage();
        exit(0);
    }
    if(strcmp(argv[1],"predict")==0){
        predictor_main(argc,argv);
    }else if(strcmp(argv[1],"learn")==0){
        learner_main(argc,argv);
    }else{
        printf("No Mode!");
        learner_usage();
        predictor_usage();
    }
    return 0;
}

