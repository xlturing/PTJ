#if !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A9__INCLUDED_)
#define AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A9__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "MediaRegistry.h"

class Book : public BaseMedia
{
public:
	Book(void);
	~Book(void);
	void Show();					// override base class show()
	void SetAuthor(char *author);	// set author
	char* GetAuthor();				// get author
	void SetGeure(char *geure);		// set geure
	char* GetGeure();				// get geure
private:
	char author[LIMIT];
	char geure[LIMIT];
};



#endif // !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A9__INCLUDED_)
