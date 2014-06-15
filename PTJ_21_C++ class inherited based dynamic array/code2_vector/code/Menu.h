// Menu.h: interface for the CMenu class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_MENU_H__40D50455_8D5E_47B5_BD91_FBD848B717E6__INCLUDED_)
#define AFX_MENU_H__40D50455_8D5E_47B5_BD91_FBD848B717E6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000



#define MAX 20			


class CMenu  
{
public:
	CMenu();
	virtual ~CMenu();
private:
	char  *items[MAX];
	int   count;
public:
	void AddItem(char *item);
	int  Choice();

};





#endif // !defined(AFX_MENU_H__40D50455_8D5E_47B5_BD91_FBD848B717E6__INCLUDED_)
