// Media.h: interface for the CMedia class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_STUDENT_H__030A8EF2_EE1E_47F6_8664_B1753867057C__INCLUDED_)
#define AFX_STUDENT_H__030A8EF2_EE1E_47F6_8664_B1753867057C__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000



#define COUNT 3			// The number of info
#define LIMIT 20		// The limit of string
#define BOOK 1
#define AUDIO 2

class BaseMedia  
{
public:
	BaseMedia();
	virtual ~BaseMedia();
protected:
	int  id;						// The media id
	char name[LIMIT];				// The media name
	char date[LIMIT];				// The input date
	int type;						// The media type
public:
	virtual void Show();								// show all
	virtual void SetId(int id);							// input id
	virtual int  GetId();								// get id
	virtual void SetDate(char *date);					// set date
	virtual char* GetDate();							// get date
	virtual void SetName(char *name);					// input name
	virtual char* GetName();							// get name
	virtual void SetType(int type);						// set type
	virtual int GetType();								// get type
};

#endif // !defined(AFX_STUDENT_H__030A8EF2_EE1E_47F6_8664_B1753867057C__INCLUDED_)
