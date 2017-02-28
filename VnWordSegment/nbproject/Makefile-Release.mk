#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_DLIB_EXT=so
CND_CONF=Release
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/DicMap.o \
	${OBJECTDIR}/Feats.o \
	${OBJECTDIR}/FeaturesSelection.o \
	${OBJECTDIR}/Machine.o \
	${OBJECTDIR}/PredictorJNI.o \
	${OBJECTDIR}/StrMap.o \
	${OBJECTDIR}/SylMap.o \
	${OBJECTDIR}/learner.o \
	${OBJECTDIR}/liblinear/blas/daxpy.o \
	${OBJECTDIR}/liblinear/blas/ddot.o \
	${OBJECTDIR}/liblinear/blas/dnrm2.o \
	${OBJECTDIR}/liblinear/blas/dscal.o \
	${OBJECTDIR}/liblinear/linear.o \
	${OBJECTDIR}/liblinear/tron.o \
	${OBJECTDIR}/main.o \
	${OBJECTDIR}/preditctor.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=-shared -m32
CXXFLAGS=-shared -m32

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/vnwordsegment

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/vnwordsegment: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/vnwordsegment ${OBJECTFILES} ${LDLIBSOPTIONS}

${OBJECTDIR}/DicMap.o: DicMap.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/DicMap.o DicMap.cpp

${OBJECTDIR}/Feats.o: Feats.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/Feats.o Feats.cpp

${OBJECTDIR}/FeaturesSelection.o: FeaturesSelection.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/FeaturesSelection.o FeaturesSelection.cpp

${OBJECTDIR}/Machine.o: Machine.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/Machine.o Machine.cpp

${OBJECTDIR}/PredictorJNI.o: PredictorJNI.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/PredictorJNI.o PredictorJNI.cpp

${OBJECTDIR}/StrMap.o: StrMap.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/StrMap.o StrMap.cpp

${OBJECTDIR}/SylMap.o: SylMap.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/SylMap.o SylMap.cpp

${OBJECTDIR}/learner.o: learner.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/learner.o learner.cpp

${OBJECTDIR}/liblinear/blas/daxpy.o: liblinear/blas/daxpy.c 
	${MKDIR} -p ${OBJECTDIR}/liblinear/blas
	${RM} "$@.d"
	$(COMPILE.c) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/liblinear/blas/daxpy.o liblinear/blas/daxpy.c

${OBJECTDIR}/liblinear/blas/ddot.o: liblinear/blas/ddot.c 
	${MKDIR} -p ${OBJECTDIR}/liblinear/blas
	${RM} "$@.d"
	$(COMPILE.c) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/liblinear/blas/ddot.o liblinear/blas/ddot.c

${OBJECTDIR}/liblinear/blas/dnrm2.o: liblinear/blas/dnrm2.c 
	${MKDIR} -p ${OBJECTDIR}/liblinear/blas
	${RM} "$@.d"
	$(COMPILE.c) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/liblinear/blas/dnrm2.o liblinear/blas/dnrm2.c

${OBJECTDIR}/liblinear/blas/dscal.o: liblinear/blas/dscal.c 
	${MKDIR} -p ${OBJECTDIR}/liblinear/blas
	${RM} "$@.d"
	$(COMPILE.c) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/liblinear/blas/dscal.o liblinear/blas/dscal.c

${OBJECTDIR}/liblinear/linear.o: liblinear/linear.cpp 
	${MKDIR} -p ${OBJECTDIR}/liblinear
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/liblinear/linear.o liblinear/linear.cpp

${OBJECTDIR}/liblinear/tron.o: liblinear/tron.cpp 
	${MKDIR} -p ${OBJECTDIR}/liblinear
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/liblinear/tron.o liblinear/tron.cpp

${OBJECTDIR}/main.o: main.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/main.o main.cpp

${OBJECTDIR}/predictor.h.gch: predictor.h 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.c) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o "$@" predictor.h

${OBJECTDIR}/preditctor.o: preditctor.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -O2 -I/opt/jdk1.7.0_75/include -I/opt/jdk1.7.0_75/include/linux -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/preditctor.o preditctor.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/vnwordsegment

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
