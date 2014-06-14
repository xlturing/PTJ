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

					
enum InfoEnum		// info enum
{
	Address = 0,
	Telephone = 1,
	Date = 2
};

typedef struct		// The structure of info
{
	char addr[LIMIT];
	char phone[LIMIT];
	char date[LIMIT];
}info;


class BaseMedia  
{
public:
	BaseMedia();
	virtual ~BaseMedia();
protected:
	int  id;
	char name[LIMIT];
	info  infos;
	int type;
public:
	void Show();								// show all
	void SetId(int id);							// input id
	int  GetId();								// get id
	void SetName(char *name);					// input name
	char* GetName();							// get name
	void SetInfo(char *info, InfoEnum flag);	// input other info
	char* GetInfo(InfoEnum flag);				// get score
	void SetType(int type);						// set type
	int GetType();								// get type
};

#endif // !defined(AFX_STUDENT_H__030A8EF2_EE1E_47F6_8664_B1753867057C__INCLUDED_)
