// MyClass.h: interface for the CMyClass class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A8__INCLUDED_)
#define AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A8__INCLUDED_




#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000


#include "BaseMedia.h"
#include "Array.h"

#define MEDIA 40						// count limit

typedef Array<BaseMedia> MediaArray;

class MediaRegistry							// definition	
{
public:
	MediaRegistry();
	virtual ~MediaRegistry();
public:
	MediaArray medias;					// definition
public:
	void AddMedia(BaseMedia* stu);		// add
	BaseMedia *SearchMedia(int id);		// find it by id
	BaseMedia *SearchMedia(char* name);	// find it by name
	int numMedia();						// number of medias
	void ListMedia();					// show all
};







#endif // !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A8__INCLUDED_)
