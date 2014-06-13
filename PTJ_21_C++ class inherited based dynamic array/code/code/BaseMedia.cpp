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
	infos.addr[0] = 0;
	infos.phone[0] = 0;
	infos.date[0] = 0;
}

BaseMedia::~BaseMedia()
{
	
}

void BaseMedia::Show()									// show all media
{
	printf("     Id: %d\n", id);
	printf("   Name: %s\n", name);
	printf("address: %s\n", infos.addr);
	printf("telephone: %s\n", infos.phone);
	printf("date: %s\n", infos.date);
	printf("type: BaseMedia\n");
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

void BaseMedia::SetInfo(char *info, InfoEnum flag)		//set info
{
	int length = strlen(info);
	if(length >= LIMIT)
		return;
	switch(flag)
	{
	case Address:
		strcpy(infos.addr,info);
		break;
	case Telephone:
		strcpy(infos.phone,info);
		break;
	case Date:
		strcpy(infos.date,info);
		break;
	default:
		return;
	}
}

char* BaseMedia::GetInfo(InfoEnum flag)						// get one info
{
	switch(flag)
	{
	case Address:
		return infos.addr;
	case Telephone:
		return infos.phone;
	case Date:
		return infos.date;
	default:
		return "Can't find";
	}
}

void BaseMedia::SetType(int type)
{
	this->type = type;
}

int BaseMedia::GetType()
{
	return this->type;
}
