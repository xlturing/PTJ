// Media.cpp: implementation of the BaseMedia class.
//
//////////////////////////////////////////////////////////////////////

#include "BaseMedia.h"
#include "string.h"
#include "stdio.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

BaseMedia::BaseMedia()
{
	id = 0;
	name[0] = 0;
	date[0] = 0;
}

BaseMedia::~BaseMedia()
{
	
}

void BaseMedia::SetId(int id)				
{
	this->id = id;										// this use					
}

int BaseMedia::GetId()
{
	return id;
}

void BaseMedia::SetName(char *pname)
{
	int length = strlen(pname);
	if(length >= LIMIT)
		return;
	strcpy(name, pname);
}

char* BaseMedia::GetName()
{
	return name;
}

void BaseMedia::SetDate(char *date)
{
	int length = strlen(date);
	if(length >= LIMIT)
		return;
	strcpy(this->date, date);
}

char* BaseMedia::GetDate()
{
	return date;
}

void BaseMedia::SetType(int type)
{
	this->type = type;
}

int BaseMedia::GetType()
{
	return this->type;
}

void BaseMedia::Show()
{
	printf("     Id: %d\n", id);
	printf("   Name: %s\n", name);
	printf("   date: %s\n", date);
	printf("   type: Book\n");
}