// Array.h: interface for the Array class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_ARRAY_H__5FA3C2B3_4C20_4A43_BD74_4DC04F2415FD__INCLUDED_)
#define AFX_ARRAY_H__5FA3C2B3_4C20_4A43_BD74_4DC04F2415FD__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#include "stdio.h"
#include "memory.h"

template <class T>
class Array  
{
public:
	Array()
	{
		elements = NULL;
		size = 0;
		count = 0;
	}

	virtual ~Array()
	{
		if(elements)
		{
			delete []elements;
		}
	}
private:
	T* elements;
	int count;
	int size;
public:
	void Add(T& t)
	{
		if(count == size)
		{
			Grow();
		}
		elements[count] = t;
		count++;
	}

	int GetCount()
	{
		return count;
	}

	int  GetSize()
	{
		return size;
	}

	void operator= (Array<T> a)
	{
		count = a.GetCount();
		size  = a.GetSize();
		elements = new T[size];
		memcpy(elements, a.elements, count*sizeof(T));
	}
	T& operator[] (int index)
	{
		return elements[index];
	}
	
private:
	void Grow()
	{
		T* elements2 = new T[size + 10];
		size += 10;
		memcpy(elements2, elements, count*sizeof(T));
		delete []elements;
		elements = elements2;
	}
};


#endif // !defined(AFX_ARRAY_H__5FA3C2B3_4C20_4A43_BD74_4DC04F2415FD__INCLUDED_)
