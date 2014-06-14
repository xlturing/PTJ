#include "stdio.h"
#include "string.h"
#include "MediaRegistry.h"
#include "menu.h"
#include "conio.h"
#include "stdlib.h"
#include "Book.h"
#include "Audio.h"

void InputMedia();		//////////////////////////////
void SearchMedia();		// input will excute in main//
void GetNum();			//////////////////////////////
void SearchName();		//////////////////////////////
void DeleteMedia();

MediaRegistry M;					// class object

void main()
{
	CMenu Menu;										//////////////////
	Menu.AddItem("List the medias");				//////////////////
	Menu.AddItem("Input a meida");					//user interface//
	Menu.AddItem("Search a media by id");			//////////////////
	Menu.AddItem("Search a media by name");			//////////////////
	Menu.AddItem("Get the number of medias");		//////////////////
	Menu.AddItem("Delete a media by id");

	do
	{
		int c = Menu.Choice();					// function choice
		switch(c)
		{
		case 1:
			M.ListMedia();
			break;
		case 2: 
			InputMedia();
			break;
		case 3:
			SearchMedia();
			break;
		case 4:
			SearchName();
			break;
		case 5:
			GetNum();
			break;
		case 6:
			DeleteMedia();
			break;
		case 0:
			return;
		}
		
	}while(true);
}

void InputMedia()
{
	int  id;
	char name[LIMIT];							// LIMIT	string limit	
	char date[LIMIT];
	int type;	// book? or contact
	
	do
	{
		printf("Id: ");
		scanf("%d", &id);
		if(id < 0)							    // id > 0
		{
			printf("Please input right id!");
			getch();
			return;
		}
		
		BaseMedia *p = M.SearchMedia(id);		// id exist?
		if(p)
		{
			printf("It's a invalid id!");
			getch();
			
			return;
		}
		
		printf("Name: ");
		scanf("%s", name);
		printf("date: ");
		scanf("%s", date);
		printf("please input media type: 1.Book or 2.Audio?");
		scanf("%d", &type);
		printf("(Other info) \n");

		Book *b = 0;
		Audio *a = 0;
		char parm1[LIMIT];
		char parm2[LIMIT];
		// new different class according to the user input
		switch(type){
		case 1:
			b = new Book;
			printf("author: ");
			scanf("%s", parm1);
			printf("geure: ");
			scanf("%s", parm2);
			break;
		case 2:
			a = new Audio;
			printf("composer: ");
			scanf("%s", parm1);
			printf("duration: ");
			scanf("%s", parm2);
			break;
		}
		if(b!=0)
		{
			b->SetId(id);
			b->SetName(name);
			b->SetDate(date);
			b->SetType(type);
			b->SetAuthor(parm1);
			b->SetGeure(parm2);

			M.AddMedia(b);
		}
		else
		{
			a->SetId(id);
			a->SetName(name);
			a->SetDate(date);
			a->SetType(type);
			a->SetComposer(parm1);
			a->SetDuration(parm2);

			M.AddMedia(a);
		}
		
									
		do
		{
			printf("Continue?(y/n): ");
			printf("\n");
			int c = getch();
			if(c == 'y' || c == 'Y')
			{
				break;
			}
			else if(c == 'n' || c == 'N')
				return;
		}while(true);
		
	}while(true);
}

void DeleteMedia()
{
	int id;
	do
	{
		printf("Please input a id (Exit: 0): ");
		scanf("%d", &id);
		if(id == NULL)
			return;
		M.DeleteMedia(id);
	}while(id);

}

void SearchMedia()
{
	int id;
	BaseMedia *p;								// media pointer
		
	do
	{
		if(M.medias.size() == 0)
		{
			printf("(Now there are none!)\n");
		}
		
		printf("Please input a id (Exit: 0): ");
		scanf("%d", &id);
		
		if(id == NULL)
			return;
		p = M.SearchMedia(id);				// find it
		if(p == NULL)
			printf("Sorry,There are not this media.\n\n");
		else 
		{
			// show accordind to type
			Book *b;
			Audio *a;
			switch(p->GetType())
			{
				case 1:
					b = static_cast<Book*>(p);
					b->Show();
					break;
				case 2:
					a = static_cast<Audio*>(p);
					a->Show();
					break;
			}
		}
		
	}while(id);
}	
	
void SearchName()
{
	char name[LIMIT];
	BaseMedia *p;								// media pointer
		
	do
	{
		if(M.medias.size() == 0)
		{
			printf("(Now there are none!)\n");
		}
		
		printf("Please input a name (Exit: 0): ");
		scanf("%s", name);
		
		if(strcmp(name,"") == 0)
			return;
		p = M.SearchMedia(name);				// find it
		if(p == NULL)
			printf("Sorry,There are not this student.\n\n");
		else 
		{
			// show accordind to type
			Book *b;
			Audio *a;
			switch(p->GetType())
			{
				case 1:
					b = static_cast<Book*>(p);
					b->Show();
					break;
				case 2:
					a = static_cast<Audio*>(p);
					a->Show();
					break;
			}
		}
		
	}while(strcmp(name,"0")!=0);
}	

void GetNum()								// get the number of all medias					
{
	//system("cls");

	CMenu Menu2;								// menu 
	
	int num;		
	num = M.numMedia();
	printf("The number of medias = %d\n", num);
	getch();
	
}
	
	
	



