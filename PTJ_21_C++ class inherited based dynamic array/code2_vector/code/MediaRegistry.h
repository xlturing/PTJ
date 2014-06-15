// MyClass.h: interface for the CMyClass class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A8__INCLUDED_)
#define AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A8__INCLUDED_




#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "BaseMedia.h"
#include "Book.h"
#include "Audio.h"
#include <vector>

using namespace std;

#define MEDIA 40						// count limit

class MediaRegistry							// definition	
{
public:
	MediaRegistry();
	virtual ~MediaRegistry();
public:
	vector<BaseMedia *> medias;				// definition medias
public:
	void AddMedia(BaseMedia* stu);		// add
	void DeleteMedia(int id);			// delete by id
	BaseMedia *SearchMedia(int id);		// find it by id
	BaseMedia *SearchMedia(char* name);	// find it by name
	int numMedia();						// number of medias
	void ListMedia();					// show all
	void sortById();					// sort all medias by their id
};







#endif // !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A8__INCLUDED_)
