#include "Book.h"
#include "BaseMedia.h"
#include "stdio.h" 
#include "string.h"

Book::Book(void)
{
}

Book::~Book(void)
{
}

void Book::Show()									// show all media
{
	printf("     Id: %d\n", id);
	printf("   Name: %s\n", name);
	printf("address: %s\n", infos.addr);
	printf("telephone: %s\n", infos.phone);
	printf("date: %s\n", infos.date);
	printf("type: Book\n");
}

