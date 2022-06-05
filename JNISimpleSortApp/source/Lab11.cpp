#include "Lab11.h"
#include <iostream>
#include <algorithm>
#include <stdlib.h>
#include<windows.h>

using namespace std;


JNIEXPORT jobjectArray JNICALL Java_Lab11_sort01
  (JNIEnv *env, jobject obj, jobjectArray arr, jobject ord){
	
	jsize len = env->GetArrayLength(arr);

	double *tab = new double[len];

	jclass cDouble = env->FindClass("java/lang/Double");
	jclass cBool = env->FindClass("java/lang/Boolean");

	for(int i = 0; i < len; i++){
		jobject objDouble = env->GetObjectArrayElement(arr, i);
		jmethodID doubleValue = env->GetMethodID(cDouble, "doubleValue", "()D");
		tab[i] = env->CallDoubleMethod(objDouble, doubleValue);
	}

	jmethodID boolValue = env->GetMethodID(cBool, "booleanValue", "()Z");

	bool order = env->CallBooleanMethod(ord, boolValue);

	if(order) sort(tab, tab + len);
	else sort(tab, tab + len, greater<>());

	jmethodID cid = env->GetMethodID(cDouble, "<init>", "(D)V");

	for(int i = 0; i < len; i++){
		jobject result = env->NewObject(cDouble, cid, tab[i]);
		env->SetObjectArrayElement(arr, i, result);
	}

	return arr;
  }

  JNIEXPORT jobjectArray JNICALL Java_Lab11_sort02
  (JNIEnv *env, jobject obj, jobjectArray arr){

		jclass lab11 = env->GetObjectClass(obj);
		jfieldID orderField = env->GetFieldID(lab11, "order", "Ljava/lang/Boolean;");
		jobject ord = env->GetObjectField(obj, orderField);

		jclass cBool = env->FindClass("java/lang/Boolean");
		jmethodID boolValue = env->GetMethodID(cBool, "booleanValue", "()Z");
		bool order = env->CallBooleanMethod(ord, boolValue);

		jsize len = env->GetArrayLength(arr);

		double *tab = new double[len];

		jclass cDouble = env->FindClass("java/lang/Double");

		for(int i = 0; i < len; i++){
			jobject objDouble = env->GetObjectArrayElement(arr, i);
			jmethodID doubleValue = env->GetMethodID(cDouble, "doubleValue", "()D");
			tab[i] = env->CallDoubleMethod(objDouble, doubleValue);
		}

		if(order) sort(tab, tab + len);
		else sort(tab, tab + len, greater<>());

		jmethodID cid = env->GetMethodID(cDouble, "<init>", "(D)V");

		for(int i = 0; i < len; i++){
			jobject result = env->NewObject(cDouble, cid, tab[i]);
			env->SetObjectArrayElement(arr, i, result);
		}

		return arr;
  }

  JNIEXPORT void JNICALL Java_Lab11_sort03
  (JNIEnv *env, jobject obj){

		jclass lab11 = env->GetObjectClass(obj);

		jfieldID aField = env->GetFieldID(lab11, "a", "[Ljava/lang/Double;");
		jfieldID orderField = env->GetFieldID(lab11, "order", "Ljava/lang/Boolean;");

		jclass cBoolean = env->FindClass("java/lang/Boolean");
		jmethodID cidB = env->GetMethodID(cBoolean, "<init>", "(Z)V");


		jclass guiClass = env->FindClass("LGui;");
		jmethodID guiConstructor = env->GetMethodID(guiClass, "<init>", "()V");
		jobject guiObject = env->NewObject(guiClass, guiConstructor);

		jfieldID setedFieldId = env->GetFieldID(guiClass, "dataSeted", "Z");
		bool seted = env->GetBooleanField(guiObject, setedFieldId);

		while(!seted){
			seted = env->GetBooleanField(guiObject, setedFieldId);
			Sleep(500);
		}

		jfieldID gaField = env->GetFieldID(guiClass, "a", "[Ljava/lang/Double;");
		jobject ga = env->GetObjectField(guiObject, gaField);

		jfieldID orrField = env->GetFieldID(guiClass, "order", "Z");
		bool ord = env->GetBooleanField(guiObject, orrField);

		jmethodID disp = env->GetMethodID(guiClass, "dispose", "()V");
		env->CallVoidMethod(guiObject, disp);
		
		jobject resultBool = env->NewObject(cBoolean, cidB, ord);
		env->SetObjectField(obj, orderField, resultBool);

		env->SetObjectField(obj, aField, ga);

		jmethodID mid = env->GetMethodID(lab11, "sort04", "()V");
		env->CallVoidMethod(obj, mid);
  }