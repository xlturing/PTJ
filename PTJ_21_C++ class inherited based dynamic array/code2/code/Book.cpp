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
	printf("Id: %d\n", id);
	printf("Name: %s\n", name);
	printf("author: %s\n", author);
	printf("geure: %s\n", geure);
	printf("date: %s\n", date);
	printf("type: Book\n\n");
}

void Book::SetAuthor(char *author)
{
	int length = strlen(author);
	if(length >= LIMIT)
		return;
	strcpy(this->author, author);
}

char* Book::GetAuthor()
{
	return author;
}

void Book::SetGeure(char *geure)
{
	int length = strlen(geure);
	if(length >= LIMIT)
		return;
	strcpy(this->geure, geure);
}

char* Book::GetGeure()
{
	return geure;
}


