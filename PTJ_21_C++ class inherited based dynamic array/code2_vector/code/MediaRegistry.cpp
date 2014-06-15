// MyClass.cpp: implementation of the MediaRegistry class.
//
//////////////////////////////////////////////////////////////////////

#include "MediaRegistry.h"
#include "stdio.h" 
#include "conio.h"
#include "string.h"
#include <algorithm>

using namespace std;

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

MediaRegistry::MediaRegistry()
{
	// init 2 media
	Book *b = new Book;
	Audio *a = new Audio;
	b->SetId(46);
	a->SetId(47);

	b->SetName("book");
	a->SetName("audio");

	b->SetType(BOOK);
	a->SetType(AUDIO);
	
	b->SetDate("2013/03/02");
	a->SetDate("2014/04/01");

	b->SetAuthor("Bruce");
	b->SetGeure("fiction");

	a->SetComposer("Michael");
	a->SetDuration("20min");

	AddMedia(a);		// add these medias
	AddMedia(b);
}


MediaRegistry::~MediaRegistry()
{
	
}

void MediaRegistry::AddMedia(BaseMedia* bm)		// add
{
	medias.push_back(bm);		
}

void MediaRegistry::DeleteMedia(int id)
{
	bool flag = false;
	vector<BaseMedia *>::iterator it;
	for(it=medias.begin();it!=medias.end();)
	{
		if(id == (*it)->GetId())
		{
			it = medias.erase(it);			// delete this media
			flag = true;
			break;
		}
		else
		{
			it++;
		}
	}
	if(!flag)
	{
		printf("Media ID:%d, Can't find!\n",id);
	}
	else
	{
		printf("Media ID:%d, Successful deleted!\n",id);
	}
}

BaseMedia* MediaRegistry::SearchMedia(int id)	// find by id
{
	vector<BaseMedia *>::iterator it;
	for(it=medias.begin();it!=medias.end();it++)
	{
		if(id == (*it)->GetId())
		{
			return (*it);			// return media
		}		
	}
	return 0;
}

BaseMedia* MediaRegistry::SearchMedia(char* name)	// find by name
{
	vector<BaseMedia *>::iterator it;
	for(it=medias.begin();it!=medias.end();it++)
	{
		if(strcmp(name,(*it)->GetName()) == 0)
		{
			return (*it);			// return media
		}		
	}	
	return 0;
}

int MediaRegistry::numMedia()			// number
{
	return medias.size();
}
 
void MediaRegistry::ListMedia()				// show all
{
	vector<BaseMedia *>::iterator it;
	for(it=medias.begin();it!=medias.end();it++)
	{
		(*it)->Show();
	}
	printf("0. Exit:");

	do
	{
		int c = getch() - '0';
		if(c == 0)
			return;
	}while(true);
}

// customize sort method
bool SortMedia (BaseMedia *m1, BaseMedia *m2) 
{
	return m1->GetId() < m2->GetId();
}

void MediaRegistry::sortById()
{
	sort(medias.begin(), medias.end(), SortMedia);
	printf("Sort Success!\n");
	getch();
}

