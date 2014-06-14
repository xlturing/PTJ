// Menu.cpp: implementation of the CMenu class.
//
//////////////////////////////////////////////////////////////////////

#include "Menu.h"
#include "stdio.h"
#include "string.h"
#include "conio.h"
#include "stdlib.h"

//////////////////////////////////////////////////////////////////////
// Construction/Destruction
//////////////////////////////////////////////////////////////////////

CMenu::CMenu()
{
	items[0] = 0;
	count   = 0;
}

CMenu::~CMenu()
{

}


void CMenu::AddItem(char *item)
{
	
	if(count >= MAX)
		return;	
	items[count] = new char[strlen(item) + 1];						// 
	strcpy(items[count], item);
	count++;
}

int CMenu::Choice()
{
	system("cls");
	int i;
	int max = strlen(items[0]);										///////////////
	for(int i = 1; i < count; i++)									// 
	{																// 
		if(max < strlen(items[i]))									// 
			max = strlen(items[i]);									// 
	}																// menu console
	printf("               ");										// 
	for(i = 0; i < max+16; i++)										//
	{																//			
		printf("*"); 												//
	}																//
	printf("\n");													///////////////
	
	for(i = 0; i < count; i++)										//
	{																//
																	//
		printf("                      %d. %s\n", i+1, items[i]);	// output menu
																	//
	}																//
	printf("                      0. Exit\n");
	printf("               ");
	
	for(i = 0; i < max+16; i++)
	{
		printf("*");
	}
	printf("\n");
	
	do
	{
		printf("\n");
		printf("Please input your choice: ");
		int c = getch() - '0';	 // if: 2 = '2' - '0'
		printf("\n");
		if(c >= 0 && c <= count)
			return c;
		else
		{
			printf("Error! Please input a right number!\n");
			getch();
		}

	}while(true);
	
}
