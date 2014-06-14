#include "Audio.h"
#include "BaseMedia.h"
#include "stdio.h" 
#include "string.h"

Audio::Audio(void)
{
}

Audio::~Audio(void)
{
}

void Audio::Show()									// show all media
{
	printf("Id: %d\n", id);
	printf("Name: %s\n", name);
	printf("duration: %s\n", duration);
	printf("composer: %s\n", composer);
	printf("date: %s\n", date);
	printf("type: Audio\n\n");
}

void Audio::SetDuration(char *duration)
{
	int length = strlen(duration);
	if(length >= LIMIT)
		return;
	strcpy(this->duration, duration);
}

char* Audio::GetDuration()
{
	return duration;
}

void Audio::SetComposer(char *composer)
{
	int length = strlen(composer);
	if(length >= LIMIT)
		return;
	strcpy(this->composer, composer);
}

char* Audio::GetComposer()
{
	return composer;
}

