// MyClass.cpp: implementation of the MediaRegistry class.
//
//////////////////////////////////////////////////////////////////////

#include "MediaRegistry.h"
#include "BaseMedia.h"
#include "stdio.h" 
#include "conio.h"
#include "string.h"
#include "Book.h"
#include "Contact.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MediaRegistry::MediaRegistry()
{
	BaseMedia med[3];		// have 3 media
	
	med[0].SetId(46);
	med[1].SetId(47);
	med[2].SetId(48);

	med[0].SetName("book");
	med[1].SetName("contact");
	med[2].SetName("intelligence");

	med[0].SetType(1);
	med[1].SetType(2);
	med[2].SetType(0);

	med[0].SetInfo("China", Address);
	med[0].SetInfo("15700879256", Telephone);
	med[0].SetInfo("2014/6/7", Date);

	med[1].SetInfo("America", Address);
	med[1].SetInfo("15450879256", Telephone);
	med[1].SetInfo("2014/6/3", Date);

	med[2].SetInfo("Australia", Address);
	med[2].SetInfo("15700803256", Telephone);
	med[2].SetInfo("2014/6/1", Date);

	for(int i = 0; i < 3; i++)
	{
		AddMedia(&med[i]);		// add this medias
	}
}


MediaRegistry::~MediaRegistry()
{
	
}



void MediaRegistry::AddMedia(BaseMedia* me)		// add
{
	medias.Add(*me);		
}

BaseMedia* MediaRegistry::SearchMedia(int id)	// find by id
{
	for(int i = 0; i < medias.GetCount(); i++)
	{
		if(id == medias[i].GetId())
		{
			return &medias[i];			// return address
		}		
	}	
	return 0;
}

BaseMedia* MediaRegistry::SearchMedia(char* name)	// find by name
{
	for(int i = 0; i < medias.GetCount(); i++)
	{
		if(strcmp(name,medias[i].GetName()) == 0)
		{
			return &medias[i];			// return address
		}		
	}	
	return 0;
}

int MediaRegistry::numMedia()			// number
{
	return medias.GetCount();
}

void MediaRegistry::ListMedia()				// show all
{
	for(int i = 0; i < medias.GetCount(); i++)
	{
		printf("  id: %d ", medias[i].GetId());
		printf("  name: %s \n", medias[i].GetName());		
		printf("  address:%s", medias[i].GetInfo(Address));
		printf("  telephone:%s", medias[i].GetInfo(Telephone));
		printf("  date:%s\n\n", medias[i].GetInfo(Date));
	}
	printf("0. Exit:");

	do
	{
		int c = getch() - '0';
		if(c == 0)
			return;
	}while(true);
}
