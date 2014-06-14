#include "stdio.h"
#include "string.h"
#include "MediaRegistry.h"
#include "menu.h"
#include "conio.h"
#include "stdlib.h"
#include "Book.h"
#include "Contact.h"

void InputMedia();		///////////////////////
void SearchMedia();		// input will excute in main
void GetNum();			///////////////////////
void SearchName();		///////////////////////

MediaRegistry M;					// class object

void main()
{
	CMenu Menu;										//////////////////
	Menu.AddItem("List the medias");				//////////////////
	Menu.AddItem("Input a meida");					// user interface//
	Menu.AddItem("Search a media by id");			//////////////////
	Menu.AddItem("Search a media by name");			//////////////////
	Menu.AddItem("Get the number of medias");		//////////////////

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
		case 0:
			return;
		}
		
	}while(true);
}

void InputMedia()
{
	int  id;
	char name[LIMIT];							// LIMIT	string limit	
	char addr[LIMIT];
	char phone[LIMIT];
	char date[LIMIT];
	int type;	// book? or contact
	
	do
	{
		printf("     Id: ");
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
		
		printf("   Name: ");
		scanf("%s", name);
		printf("(Other info) \n");
		printf("Address: ");
		scanf("%s", &addr);
		printf("Telephone: ");
		scanf("%s", &phone);
		printf("date: ");
		scanf("%s", &date);
		printf("please input media type: 1.Book or 2.Contact?");
		scanf("%d", &type);
		printf("\n");

		BaseMedia *S;
		// new different class according to the user input
		switch(type){
		case 1:
			S = new Book;
			S->SetType(1);
			break;
		case 2:
			S = new Contact;
			S->SetType(2);
			break;
		default:
			S = new BaseMedia;
			S->SetType(0);
			break;
		}
												//////////////////
		S->SetId(id);							//				//		
		S->SetName(name);						//	input other	//
		S->SetInfo(addr, Address);				// 	media info	//
		S->SetInfo(phone, Telephone);			//				//
		S->SetInfo(date, Date);					//////////////////		
		
		M.AddMedia(S);							
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


void SearchMedia()
{
	int id;
	BaseMedia *p;								// media pointer
		
	do
	{
		if(M.medias.GetCount() == 0)
		{
			printf("(Now there are none!)\n");
		}
		
		printf("Please input a id (Exit: 0): ");
		scanf("%d", &id);
		
		if(id == NULL)
			return;
		p = M.SearchMedia(id);				// find it
		if(p == NULL)
			printf("Sorry,There are not this student.\n\n");
		else 
		{
			// show accordind to type
			Book *b;
			Contact *c;
			switch(p->GetType())
			{
				case 1:
					b = static_cast<Book*>(p);
					b->Show();
					break;
				case 2:
					c = static_cast<Contact*>(p);
					c->Show();
					break;
				default:
					p->Show();
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
		if(M.medias.GetCount() == 0)
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
			Contact *c;
			switch(p->GetType())
			{
				case 1:
					b = static_cast<Book*>(p);
					b->Show();
					break;
				case 2:
					c = static_cast<Contact*>(p);
					c->Show();
					break;
				default:
					p->Show();
					break;
			}
		}
		
	}while(strcmp(name,"0")!=0);
}	

void GetNum()								// get the number of all medias					
{
	//system("cls");

	CMenu Menu2;								// menu 
	Menu2.AddItem("Address");
	Menu2.AddItem("Telephone");
	Menu2.AddItem("Date");
	
	int num;		
	num = M.numMedia();
	printf("The number of medias = %d\n", num);
	getch();
	
}
	
	
	



