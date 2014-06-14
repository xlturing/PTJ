#if !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A7__INCLUDED_)
#define AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A7__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "MediaRegistry.h"

class Audio : public BaseMedia
{
public:
	Audio(void);
	~Audio(void);
	void Show();						// override base class show()
	void SetDuration(char *author);		// set duration
	char* GetDuration();				// get duration
	void SetComposer(char *geure);		// set composer
	char* GetComposer();				// get composer
private:
	char duration[LIMIT];
	char composer[LIMIT];
};



#endif // !defined(AFX_MYCLASS_H__79312B1F_01FD_49AD_A710_8A076607F8A7__INCLUDED_)
