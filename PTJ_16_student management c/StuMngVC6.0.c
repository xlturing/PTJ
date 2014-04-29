//=======================
// File: StuManagement.c
// Time: 2014/04/29
// Vers: Release V1.0
// Auth:
// Password: 123
//=======================

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <windows.h>			// for Sleep()
#include <math.h>				// for abs()

#define RELEASE_VERS			STU_MNG_SUCCESS  // DEBUGGING_VERS：Debug version，RELEASE_VERS：release version

#ifndef STU_MNG_FAIL
#define STU_MNG_FAIL			(0)
#define STU_MNG_SUCCESS			(!STU_MNG_FAIL)
#endif  // @ #ifndef STU_MNG_FAIL

#ifndef STU_MNG_FALSE
#define STU_MNG_FALSE			(0)
#define STU_MNG_TRUE			(!STU_MNG_FALSE)
#endif  // @ #ifndef STU_MNG_FALSE

#define ERR_UNFOUND				(-1)
#define ERR_WARNING()			printf("\a")

#define PRINT_ITEM()			printf("        Name    SID    Sex     CA      Exam   SumScore  AveScore\n")
#define PRINT_LINE()			printf("  -----------------------------------------------------------------------------\n")

#define IGNORE_2_END_OF_LINE()	while ( getchar() != '\n' )  // ignore redundant input til end-of-line

// == Part1: Type Declaration =======================================================================================================

#define STUSTR_SIZE     25  // the number of sutdents
typedef struct StuInfom_ {          // Structure of student info
	char  name[STUSTR_SIZE];        // name
	int   sid;                      // SID
	char  Sex;                      // Sex (G: girl, B: boy)
	float ca;                       // Continuous Assessment score
	float exam;                     // Examination score
	struct StuInfom_ *NextStu;
} StuInfom;

#define PAWORD_SIZE     60
typedef struct SysDevMng__ {        // System Manager
	int SumStu;					    // The number of students
	char PaWord[PAWORD_SIZE];       // System login password
	StuInfom *TheFirstStu;			// The head pointer of students linked list
	StuInfom *TheLeastStu;			// Point to the last student
} SysDevMng_;

// == System Global Variable =======================================================================================================

SysDevMng_ SysDevMng;				// Global Variable: system manager

#if DEBUGGING_VERS
#define   INIT_SIZE          5
StuInfom StuInitTab[INIT_SIZE] = {
	    /* Name         SID    Sex    CA      Exam  */
		{ "Jams",	  2014243, 'B',  88.5,    92.0 },
		{ "Lily",	  2014116, 'G',  92.0,    32.5 },
		{ "Tom",	  2014503, 'B',  5.5,     88.0 },
		{ "Lynda",	  2014423, 'G',  52.0,    7.0 },
		{ "Philips",  2014368, 'B',  45.0,    65.5 }
};
#endif // @ #if DEBUGGING_VERS

// == Part2: Method Declaration =======================================================================================================

/* The method is relevant to students linked list */
void StuSysMngInit(SysDevMng_ *SysMng);		// Init system
void EntrStuInfo(StuInfom *StuInfo);		// Input student info
int AddStu(void);							// Add a student
int DeleteStuStr(const char *str);			// Delete a student node by string
int DeleteStu(void);						// Delete student
void PasswordInput(void);                   // Input password
void ChangePassword(void);                  // Modify password
void ShowHomePage(void);					// Show the window, and display the hint
void StuSysMngRun(void);					// Run system
void AddStuInfo(const StuInfom *StuInfo);   // Add the sutdent which has info
void CopyStuInfo(StuInfom *StuInfoDest, const StuInfom *StuInfoSorce);  // Copy student structure
void DispStuInfo(const StuInfom *Stu);		// Display a student's info
void DispAllStu(void);						// Display all students' info
void DispSelection(void);					// Display the selected student's info
void RunSlection(void);						// Perform by user selection
void QuitSys (void);						// Exit system
void Save2File(void);						// Save all students info to file
void LoadFrmFile(void);						// Load all students info from file

/* The method is relevant to sort */
int SelecSortedInfo(void);                                  // Sort method select
void DispSortedInfo(void);                                  // Display result according to the user selection
StuInfom **GetPtrStuTab(void);                              // Get the pointers array which point to student node
int NameCmp(const void *stu1, const void *stu2);            // Compare name
void SortByName(void);                                      // Sort by name
int SIDCmp(const void *stu1, const void *stu2);             // Compare sid
void SortBySID(void);									    // Sort by sid
int FloatCmp(const float f1, const float f2);               // Compare float
int SumScoreCmp(const void *stu1, const void *stu2);        // Compare sum score
void SortBySumScore(void);									// Sort by sum score
void SortBySbjct(void);										// Sort by subject
void DispAllStuByPtrStuInfomTab(StuInfom **tab, const int size);  // Show all
int SelecOfSortBySbjct(void);
void SortByTheSubject(int subject);
void SortBySbjct(void);

// == Part3: UnPassed student type Declaration =======================================================================================

typedef struct {					// UnPassed student info
	char  name[STUSTR_SIZE];        // UnPassed student name
	char  UnpsSubj[STUSTR_SIZE];    // UnPassed student：ca、exam
	float UnpsScore;                // UnPassed student score
} UnpassStuInfo;

struct {
	UnpassStuInfo *pUnpInfo;		// Point to unpassed student linked list
	struct {						// Fail field
		unsigned int Sum : 8;		// Unpassed student number
		unsigned int CA  : 8;		// CA Unpassed student number
		unsigned int Exam  : 8;		// Exam Unpassed student number
	} UnPassInfo;
} UnPassDev;						// Global variable, unpassed info manager

#define   UNPASS_SUM  /* Unpassed student number   */  UnPassDev.UnPassInfo.Sum
#define   UNPASS_CA   /* CA Unpassed student number */  UnPassDev.UnPassInfo.CA
#define   UNPASS_Exam   /* Exam Unpassed student number */  UnPassDev.UnPassInfo.Exam
#define   UNPASS_CA_Exam                   (UNPASS_CA + UNPASS_Exam)

void LoadUnpassStuInfo(StuInfom *stu);    // load all unpassed student info
void DispUnpassInfo(void);                // display unpassed info

// == main fucntion =========================================================================================================

int main (void)
{
	ERR_WARNING();
	StuSysMngInit(&SysDevMng);
	StuSysMngRun();

	return 0;
}

// == subfunction =====================================================================================================

//===================================================================
// Function: Init System
// Parameter: System manager structure pointer
// Return: none
//===================================================================
// DEBUGGING_VERS:：debugging version，RELEASE_VERS：release version
//===================================================================
void StuSysMngInit ( SysDevMng_ *SysMng )
{
#if DEBUGGING_VERS
	int index;
#endif // @ #if DEBUGGING_VERS

	SysMng->SumStu = 0;
	SysMng->TheFirstStu = SysMng->TheLeastStu = NULL;

#if DEBUGGING_VERS
	for ( index = 0; index < INIT_SIZE; index++) {
		AddStuInfo ( &StuInitTab[index] );
	}
#endif // @ #if DEBUGGING_VERS

#if RELEASE_VERS
	LoadFrmFile();
#endif // @ #if RELEASE_VERS
}

//==========================================================
// Function: Run system
// Parameter: none
// Return: none
//==========================================================
void StuSysMngRun ( void )
{
	ShowHomePage();
#if RELEASE_VERS
	PasswordInput();
#endif // @ #if RELEASE_VERS
	DispSelection();
	RunSlection();
}

//==========================================================
// Function: Display window
// Parameter: none
// Return: none
//==========================================================
void ShowHomePage(void)
{
	putchar('\n');
	printf("         ===========================================================        \n");
	printf("         **                                                       **        \n");
	printf("         **          Student Information Management System        **        \n");
	printf("         **                  Copyright(C) of Harry                **        \n");
	printf("         **               TIME: %s, %s             **\n", __DATE__, __TIME__   );
	printf("         **                                                       **        \n");
	printf("         ===========================================================        \n");
	putchar('\n');

}

//==========================================================
// Function: Input password
// Parameter: none
// Return: none
//==========================================================
void PasswordInput( void )
{
	char paTmp[PAWORD_SIZE];

	puts("\n  Please input your password: \n");
	gets(paTmp);

	while ( strcmp(paTmp, SysDevMng.PaWord) != 0 ) {
		fputs("  Wrong  password!\n  Please input your password: \n", stderr);
		ERR_WARNING();
		gets(paTmp);
	}

	strcpy(SysDevMng.PaWord, paTmp);
}

//==========================================================
// Function: Modify password
// Parameter: none
// Return: none
//==========================================================
void ChangePassword ( void )
{
	int OldPawrdLen;
	char paTmp1[PAWORD_SIZE],
		 paTmp2[PAWORD_SIZE];

	OldPawrdLen = strlen(SysDevMng.PaWord);

	if ( OldPawrdLen == 0 ) {
		puts("\n  No password, please create a password: \n");
		LableA:	gets(paTmp1);
		puts("\n  Please input again: \n");
		gets(paTmp2);

		if ( strcmp(paTmp1, paTmp2) == 0 ) {
			puts("\n  Created a password successfully! \n");
		} else {
			fputs("\n  The two passwords you entered do not match, please input again: \n", stderr);
			ERR_WARNING();
			goto LableA;
		}

	} else {
		puts("\n  Please input a new password: \n");
		LableB:	gets(paTmp1);

		while ( strcmp(paTmp1, SysDevMng.PaWord) == 0 ) {
			fputs("\n  The password you input already exists! Please input a new password again: \n", stderr);
			ERR_WARNING();
			gets(paTmp1);
		}

		puts("\n  Please input again: \n");
		gets(paTmp2);

		if ( strcmp(paTmp1,paTmp2) == 0 ) {
			puts("\n  Created a password successfully! \n");
		} else {
			fputs("\n  The two passwords you entered do not match, please input again: \n", stderr);
			ERR_WARNING();
			goto LableB;
		}
	}

	strcpy(SysDevMng.PaWord, paTmp2);
	Sleep(750);
	DispSelection();
}

//==========================================================
// Function: Display user selection and hints
// Parameter: none
// Return: none
//==========================================================
void DispSelection ( void )
{
	putchar('\n');
	puts("  Please input the digit <1-7> to select the items: \n"                  );
	puts("       --------------------------------------------------------------- " );
	puts("             1> Scan all stus' info        2> Sorted  stus' info  "      );
	puts("             3> Add stu's info             4> Delete  stu's info  "      );
	puts("             5> Scan Unpass info           6> Change password     "      );
	puts("             7> Quit from Sys                                     "      );
	puts("       --------------------------------------------------------------- " );
	printf("                                             Now the SumStu is %d\n", SysDevMng.SumStu);
	puts("       --------------------------------------------------------------- \n");
	putchar('\n');
}

//==========================================================
// Function: Copy a student structure
// Parameter: StuInfom *StuInfoDest, const StuInfom *StuInfoSorce
// Return: void
//==========================================================
void CopyStuInfo ( StuInfom *StuInfoDest, const StuInfom *StuInfoSorce )
{
	strcpy(StuInfoDest->name,   StuInfoSorce->name);
	StuInfoDest->sid = StuInfoSorce->sid;
	StuInfoDest->Sex      = StuInfoSorce->Sex;
	StuInfoDest->ca       = StuInfoSorce->ca;
	StuInfoDest->exam  = StuInfoSorce->exam;
}

//==========================================================
// Function: type-in a student's info
// Parameter: pointer
// Return: none
//==========================================================
void EntrStuInfo ( StuInfom *StuInfomStruct )
{
	puts("  Please input the stu's name (eg: Sam) ：" );
	scanf("  %s",StuInfomStruct->name ); IGNORE_2_END_OF_LINE();
	puts("  Please input the stu's number (eg: 2014999) ：" );
	scanf("  %d",&StuInfomStruct->sid); IGNORE_2_END_OF_LINE();
	puts("  Please input the stu's sex (B: boy, G: girl) ：" );
	scanf("  %c",&StuInfomStruct->Sex); IGNORE_2_END_OF_LINE();
	puts("  Please input the stu's CA (eg: 95.5) ：" );
	scanf("  %f",&StuInfomStruct->ca); IGNORE_2_END_OF_LINE();
	puts("  Please input the stu's Exam (eg: 95.5) ：" );
	scanf("  %f",&StuInfomStruct->exam); IGNORE_2_END_OF_LINE();
}

//==========================================================
// Function: Add a student which have info
// Parameter: pointer
// Return: void
//==========================================================
void AddStuInfo ( const StuInfom *StuInfo )
{
	StuInfom *TempStu /*= StuInfo*/ ;

	if ( (TempStu = (StuInfom *)malloc( sizeof(StuInfom) )) == NULL ) {
		fputs("\n  Malloc failly, unknown error!\n", stderr);
		ERR_WARNING();
		return;
	}

	if ( !SysDevMng.TheFirstStu ) {
		SysDevMng.TheFirstStu = TempStu;
		SysDevMng.TheLeastStu = TempStu;
	} else {
		SysDevMng.TheLeastStu->NextStu = TempStu;
		SysDevMng.TheLeastStu = TempStu;
	}

	SysDevMng.TheLeastStu->NextStu = NULL;
	CopyStuInfo ( SysDevMng.TheLeastStu, StuInfo );
	SysDevMng.SumStu++;
}

//==========================================================
// Function: Add a student
// Parameter: none
// Return: STU_MNG_SUCCESS, STU_MNG_FAIL;
//==========================================================
int AddStu ( void )
{
	StuInfom *TempStu;

	if ( (TempStu = (StuInfom *)malloc( sizeof(StuInfom) )) == NULL ) {
		fputs("\n  Malloc failly, unknown error!\n", stderr);
		ERR_WARNING();
		return STU_MNG_FAIL;
	}

	if ( !SysDevMng.TheFirstStu ) {
		SysDevMng.TheFirstStu = TempStu;
		SysDevMng.TheLeastStu = TempStu;
	} else {
		SysDevMng.TheLeastStu->NextStu = TempStu;
		SysDevMng.TheLeastStu = TempStu;
	}

	SysDevMng.TheLeastStu->NextStu = NULL;
	EntrStuInfo ( SysDevMng.TheLeastStu );
	SysDevMng.SumStu++;
	printf("\n  Add Stu successfully!\n\n");
	DispAllStu ();
	return STU_MNG_SUCCESS;
}

//==========================================================
// Function: Delete student by name
// Parameter: name
// Return: STU_MNG_SUCCESS, STU_MNG_FAIL
//==========================================================
int DeleteStuStr ( const char *str )
{
	StuInfom *PrePtr = NULL,
			 *CurPtr = SysDevMng.TheFirstStu;

	while ( CurPtr && strcmp(CurPtr->name, str) != 0 ) {
		PrePtr = CurPtr;
		CurPtr = CurPtr->NextStu;
	}

	if ( CurPtr == NULL ) {      // cannot find
		fputs("\n  The stu Unfound!", stderr );
		ERR_WARNING();
		return STU_MNG_FAIL;
	}

	if ( CurPtr == SysDevMng.TheFirstStu ) {
		SysDevMng.TheFirstStu = CurPtr->NextStu;
		free(CurPtr);
	} else {										//
		PrePtr->NextStu = CurPtr->NextStu;
		free(CurPtr);
	}

	SysDevMng.SumStu--;
	return STU_MNG_SUCCESS;
}

//==========================================================
// Function: Delete a student
// Parameter: none
// Return: STU_MNG_SUCCESS, STU_MNG_FAIL
//==========================================================
int DeleteStu ( void )
{
	int res;
	char temp[STUSTR_SIZE];

	puts("\n  Please input Stu's name (eg: Tom) ：\n" );
	gets(temp);

	res = DeleteStuStr(temp);

	if ( res == STU_MNG_FAIL ) {
		fputs("  Delete Stu failly!\n", stderr);
		ERR_WARNING();
	} else {
		puts("  Delete Stu successfully!\n\n");
	}

	DispAllStu();
	return res;
}

//==========================================================
// Function: Display a student's total info
// Parameter: pointer
// Return: none
//==========================================================
void DispStuInfo ( const StuInfom *Stu )
{
	printf("  %10s  %d   %c    %5.1f    %5.1f    %5.1f    %5.1f\n",
			  Stu->name,
			  Stu->sid,
			  Stu->Sex,
			  Stu->ca,
			  Stu->exam,
			  Stu->ca + Stu->exam,
			  (Stu->ca + Stu->exam)/2.0 );
}

//==========================================================
// Function: Display all student info(according to add sort)
// Parameter: none
// Return: none
//==========================================================
void DispAllStu ( void )
{
	StuInfom *tmp = SysDevMng.TheFirstStu;

	puts("  All StuInfo: \n");
	PRINT_LINE();
	PRINT_ITEM();
	PRINT_LINE();

	while ( tmp ) {
		DispStuInfo (tmp);
		tmp = tmp->NextStu;
	}

	PRINT_LINE();
	printf("                                                   Now the SumStu is %d\n",SysDevMng.SumStu);
	PRINT_LINE();
	//printf("\n\n");
	Sleep(750);
	DispSelection();
}

//=================================================================
// Function: Load unpassed student info
// Parameter: StuInfom *stu：point to unpassed student linked list
// Return: none
//=================================================================
void LoadUnpassStuInfo( StuInfom *stu )
{
	UNPASS_SUM = UNPASS_CA = UNPASS_Exam = 0;

	if ( stu == NULL ) {
		UnPassDev.pUnpInfo = NULL;
		fputs("  No StuInfo!\n", stderr);
		ERR_WARNING();
		return;
	}

	while ( stu ) {

		if ( stu->ca < 60.0 || stu->exam < 60.0  ) {

			UNPASS_SUM++;

			if ( stu->ca < 60.0 ) {   // CA fail
				UnPassDev.pUnpInfo = (UnpassStuInfo *)realloc( UnPassDev.pUnpInfo, (UNPASS_CA_Exam + 1) * sizeof(UnpassStuInfo) );
				if ( UnPassDev.pUnpInfo == NULL ) {
					fputs("  Unknown Error!", stderr); ERR_WARNING();
					return;
				} else {
					strcpy( (UnPassDev.pUnpInfo + UNPASS_CA_Exam)->name, stu->name );
					strcpy( (UnPassDev.pUnpInfo + UNPASS_CA_Exam)->UnpsSubj, "Chinese" );
					(UnPassDev.pUnpInfo + UNPASS_CA_Exam)->UnpsScore = stu->ca;
				}
				UNPASS_CA++;
			}

			if ( stu->exam < 60.0 ) {   // Exam fail
				UnPassDev.pUnpInfo = (UnpassStuInfo *)realloc( UnPassDev.pUnpInfo, (UNPASS_CA_Exam + 1) * sizeof(UnpassStuInfo) );
				if ( UnPassDev.pUnpInfo == NULL ) {
					fputs("  Unknown Error!", stderr); ERR_WARNING();
					return;
				} else {
					strcpy( (UnPassDev.pUnpInfo + UNPASS_CA_Exam)->name, stu->name );
					strcpy( (UnPassDev.pUnpInfo + UNPASS_CA_Exam)->UnpsSubj, "Exam" );
					(UnPassDev.pUnpInfo + UNPASS_CA_Exam)->UnpsScore = stu->exam;
				}
				UNPASS_Exam++;
			}
		}

		stu = stu->NextStu;

	} // end of while (stu)
}

//==========================================================
// Function: Display unpassed info
// Parameter: none
// Return: none
//==========================================================
void DispUnpassInfo ( void )
{
	unsigned int idx;

	LoadUnpassStuInfo ( SysDevMng.TheFirstStu );

	if ( UnPassDev.pUnpInfo == NULL) {               // 如果none不及格学生
		fputs("  Not found UnpassStu!\n", stderr);
		ERR_WARNING();
	} else {
		printf("  UnPassesStu info: \n");
		printf("    ------------------------------------\n");
		printf("       UnpassSum   UnpassCh   UnpassMa  \n");
		printf("    ------------------------------------\n");
		printf("          %d          %d           %d   \n", UNPASS_SUM, UNPASS_CA, UNPASS_Exam);
		printf("    ------------------------------------\n");
		printf("      UnpassStuName    UnpassSubj       \n");
		printf("    ------------------------------------\n");

		for ( idx = 0; idx < (UNPASS_CA_Exam); idx++ ) {
				  /*   姓名       科目          成绩      */
			printf("   %10s       %10s          %.1f      \n",
				  (UnPassDev.pUnpInfo + idx)->name, (UnPassDev.pUnpInfo + idx)->UnpsSubj, (UnPassDev.pUnpInfo + idx)->UnpsScore);
		}

		printf("    -----------------------------------------------\n\n");
		Sleep(750);
		DispSelection();

		free(UnPassDev.pUnpInfo);
	}
}

//==========================================================
// Function: Save all student info to binary file
// Parameter: none
// Return: none
//==========================================================
void Save2File ( void )
{
	FILE      *pF;
	StuInfom  *pStu;

	if ( ( pF = fopen( "StuInfo.dat", "wb") ) == NULL ) {  // "wb":以只写的方式打开或建立一个二进制文件
		fputs("  Can't save stuinfo into file! Please requit the sys.\n", stderr);
		ERR_WARNING();
		return;
	}

	rewind(pF);     // File pointer

	/* Save the number of sutdents */
	fwrite( &SysDevMng.SumStu, sizeof(int), 1, pF);

	/* Save password */
	fwrite( &SysDevMng.PaWord, sizeof(SysDevMng.PaWord), 1, pF);

	/* Save student info */
	for ( pStu = SysDevMng.TheFirstStu; pStu; pStu = pStu->NextStu ) {
		fwrite( pStu, sizeof(StuInfom), 1, pF );
	}

	printf("  There are %d stu saved!\n\n", SysDevMng.SumStu);
	fclose(pF);
}

//==========================================================
// Function: Load Student info from file
// Parameter: none
// Return: none
//==========================================================
void LoadFrmFile ( void )
{
	int       tmp,
		      idx;
	FILE      *pF;
	StuInfom  tmpStu;

	if ( ( pF = fopen( "StuInfo.dat", "rb") ) == NULL ) {  // "rb": Create a read only Binary File
		fputs("  Can't load stuinfo form system file! Please restart the sys.\n", stderr);
		ERR_WARNING();
		return;
	}

	rewind(pF);

	/* The number of student */
	fread( &tmp, sizeof(int), 1, pF );

	/* Password */
	fread( &SysDevMng.PaWord, sizeof(SysDevMng.PaWord), 1, pF );

	/* Load student info */
	for ( idx = 0; idx < tmp; idx++ ) {
		fread( &tmpStu, sizeof(StuInfom), 1, pF);
		AddStuInfo ( &tmpStu );
	}

	printf("  There are %d stu loaded!\n", tmp);
	fclose(pF);
}

//==========================================================
// Function: Exit System
// Parameter: none
// Return: none
//==========================================================
void QuitSys ( void )
{
	int cnt = 3;

	Save2File();

	do
	{
		printf("  System will quit in %ds.", cnt);
		printf("\r");
		Sleep(1000);    // delay 1s
	} while (--cnt);

	printf("  System will quit in %ds.", cnt);
	printf("\n\n");
	exit(0);
}

#define SCAN_ALL		1
#define SORTED_INF		2
#define ADD_STU			3
#define DEL_STU			4
#define SCAN_UNPASS		5
#define CHANGE_PSWRD	6
#define QUIT_SYS		7

//==========================================================
// Function: Perform by user selection
// Parameter: none
// Return: none
//==========================================================
void RunSlection ( void )
{
	int Selc;

	while (STU_MNG_TRUE) {
		scanf("%d",&Selc);
		IGNORE_2_END_OF_LINE();

		switch ( Selc) {

			case SCAN_ALL:
							 DispAllStu();
							 break;

			case SORTED_INF:
							 DispSortedInfo();
							 break;

			case ADD_STU:
							 AddStu();
							 break;

			case DEL_STU:
							 DeleteStu();
							 break;

			case SCAN_UNPASS:
							 DispUnpassInfo();
							 break;

			case CHANGE_PSWRD:
							 ChangePassword();
							 break;

			case QUIT_SYS:
							 QuitSys();
							 break;

			default:
							 fputs("\n  Error! Please input specific selection <1-7> !\n", stderr);
							 ERR_WARNING();
							 DispSelection ();
		}
	}
}

// == Method about sort =========================================================================================================

#define   SORT_NAME      1
#define   SORT_SID    2
#define   SORT_SUMSCORE  3
#define   SORT_SUBJECT   4

//==========================================================
// Function: Tell user input selection and return it
// Parameter: none
// Return: user input int
//==========================================================
int SelecSortedInfo ( void )
{
	int Selec;

	puts("  Please input the selection <1-4> of sorted info according to :");
	puts("    ----------------------------------------------------");
	puts("            1> Name              2> SID ");
	puts("            3> SumScore          4> Subject ");
	puts("    ----------------------------------------------------");

	//scanf("%d", &Selec);
	//IGNORE_2_END_OF_LINE();

	while( !( (scanf("%d", &Selec) == 1) && (Selec >= 1 && Selec <= 4) ) ) {
			IGNORE_2_END_OF_LINE();
			puts("  Input Error! Please input the selection <1-4> of sorted info according to :");
	}
	IGNORE_2_END_OF_LINE();
	return Selec;
}

//==========================================================
// Function: Display sort info
// Parameter: none
// Return: none
//==========================================================
void DispSortedInfo ( void )
{
	switch ( SelecSortedInfo() ) {

		case SORT_NAME :    // By name
							SortByName();
							Sleep(750);
							DispSelection ();
							break;

		case SORT_SID:   // By SID
							SortBySID();
							Sleep(750);
							DispSelection ();
							break;

		case SORT_SUMSCORE: // By sum score
							SortBySumScore();
							Sleep(750);
							DispSelection ();
							break;

		case SORT_SUBJECT:  // By subject
							SortBySbjct();
							Sleep(750);
							DispSelection ();
							break;
	}
}

//==========================================================
// Function: Get a array which contain the StuInfo* type
// Parameter: none
// Return: StuInfo* head
//==========================================================
StuInfom **GetPtrStuTab ( void )
{
	StuInfom *tmp,
			 **tab,
			 **index;

	tmp = SysDevMng.TheFirstStu;
	index = tab = (StuInfom **)malloc( SysDevMng.SumStu * sizeof(StuInfom *) );

	if (tab == NULL) {
		fputs("  Unknown Error! Can't sorting!\n", stderr);
		ERR_WARNING();
		return NULL;
	}

	while ( tmp ) {
		*(index++) = tmp;
		tmp = tmp->NextStu;
	}

	return tab;
}

//==========================================================
// Function: Display all students info
// Parameter: tab：StuInfom * head, size: array length
// Return: none
//==========================================================
void DispAllStuByPtrStuInfomTab ( StuInfom **tab, const int size )
{
	int index;

	PRINT_LINE();
	PRINT_ITEM();
	PRINT_LINE();

	for ( index=0; index < size; index++ ) {
		DispStuInfo (*tab++);
	}

	PRINT_LINE();
	printf("                                                    Now the SumStu is %d\n", SysDevMng.SumStu);
	PRINT_LINE();
	//printf("\n\n");
}

//==========================================================
// Function: Compare float
// Parameter: f1、f2
// Return: 1：f1 > f2，0：f1 == f2，-1：f1 < f2
//==========================================================
int FloatCmp ( const float f1, const float f2 )
{
	if ( fabs(f1-f2) < 0.000001 ) {
		return 0;
	} else {
		return (f1-f2) > 0 ? 1 : -1;
	}
}

//==============================================================
// Function: Processing student sort by name and display result
// Parameter: none
// Return: none
//==============================================================
void SortByName ( void )
{
	int i,
		j;
	StuInfom *tmp,
	         **table;

	table = GetPtrStuTab();
	if (table == NULL) {
		fputs("  Unknown Error!\n", stderr);
		ERR_WARNING();
	} else {
		for ( i = 0; i < SysDevMng.SumStu-1; i++ )
			for ( j = i+1; j < SysDevMng.SumStu; j++ )
				if ( strcmp( table[i]->name, table[j]->name ) > 0 ) {    // ascending
					tmp = table[i];
					table[i] = table[j];
					table[j] = tmp;
				}

		puts("  Sorted stu info by Name: ");
		DispAllStuByPtrStuInfomTab ( table, SysDevMng.SumStu );
	}

	free(table);
}

//=============================================================
// Function: Processing student sort by sid and display result
// Parameter: none
// Return: none
//=============================================================
void SortBySID(void)
{
	int i,
		j;
	StuInfom *tmp,
	         **table;

	table = GetPtrStuTab();
	if (table ==NULL) {
		printf("  Unknown Error!\n");
		ERR_WARNING();
	} else {
		for ( i = 0; i < SysDevMng.SumStu-1; i++ )
			for ( j = i+1; j < SysDevMng.SumStu; j++ )
				if (table[i]->sid > table[j]->sid ) {    // ascending sort
					tmp = table[i];
					table[i] = table[j];
					table[j] = tmp;
				}

		puts("  Sorted stu info by SID: ");
		DispAllStuByPtrStuInfomTab ( table, SysDevMng.SumStu );
	}

	free(table);
}

//============================================================================
// Function: Processing student sort by student sum scores and display result
// Parameter: none
// Return: none
//=============================================================================
void SortBySumScore(void)
{
	int i,
		j;
	StuInfom *tmp,
	         **table;

	table = GetPtrStuTab();
	if (table ==NULL) {
		fputs("  Unknown Error!\n", stderr);
		ERR_WARNING();
	} else {
		for ( i = 0; i < SysDevMng.SumStu-1; i++ )
			for ( j = i+1; j < SysDevMng.SumStu; j++ ) {

				float sum1 = table[i]->ca + table[i]->exam;		// table[i] student total soores
				float sum2 = table[j]->ca + table[j]->exam;		// table[j] student total soores

				if ( FloatCmp( sum1, sum2 ) < 0 ) {
					tmp = table[i];
					table[i] = table[j];
					table[j] = tmp;
				}
			}

		puts("  Sorted stu info by SumScore: ");
		DispAllStuByPtrStuInfomTab ( table, SysDevMng.SumStu );
	}

	free(table);
}

#define   SORT_BY_CA      1
#define   SORT_BY_EX      2
#define   SORT_BY_EN      3

//==========================================================
// Function: User select a subject to sort
// Parameter: none
// Return: User selection int type
//==========================================================
int SelecOfSortBySbjct(void)
{
	int Selec;

	puts("  Please input the subject <1-3> of sorted info according to :");
	puts("    -------------------------------------");
	puts("         1> CA          2> Exam          ");
	puts("    -------------------------------------");

	while( !( (scanf("%d", &Selec) == 1) && (Selec >= 1 && Selec <= 2) ) ) {
			IGNORE_2_END_OF_LINE();
			fputs("  Input Error! Please input the subject <1-2> of sorted info according to :", stderr);
			ERR_WARNING();
	}
	IGNORE_2_END_OF_LINE();

	return Selec;
}

//==========================================================
// Function: Sort by user selection
// Parameter: subject
// Return: none
//==========================================================
void SortByTheSubject(int subject)
{
	int i,
		j;
	StuInfom *tmp,
	         **table;

	table = GetPtrStuTab();
	if (table ==NULL) {
		fputs("  Unknown Error!\n", stderr);
		ERR_WARNING();
	} else {
		for ( i=0; i<SysDevMng.SumStu-1; i++ ) {
			for ( j=i+1; j<SysDevMng.SumStu; j++ ) {

				float score1 = 0.0, score2 = 0.0;

				if (subject == SORT_BY_CA ) {				/* Sort by CA */
					score1 = table[i]->ca ;
					score2 = table[j]->ca ;
					//puts("  Sorted stu info by ca: ");
				} else if (subject == SORT_BY_EX ) {		/* Sort by Exam */
					score1 = table[i]->exam ;
					score2 = table[j]->exam ;
					//puts("  Sorted stu info by exam: ");
				}

				if ( FloatCmp( score1, score2 ) < 0 ) {
					tmp = table[i];
					table[i] = table[j];
					table[j] = tmp;
				}
			}
		}

		if (subject == SORT_BY_CA ) {				/* Sort by CA */
			puts("  Sorted stu info by ca: ");
		} else if (subject == SORT_BY_EX ) {		/* Sort by Exam */
			puts("  Sorted stu info by exam: ");
		}

		DispAllStuByPtrStuInfomTab(table, SysDevMng.SumStu);
	}

	free(table);
}

#define SortByCA()  SortByTheSubject( SORT_BY_CA )
#define SortByExam()  SortByTheSubject( SORT_BY_EX )

//==========================================================
// Function: Display sort info by user selection
// Parameter: none
// Return: none
//==========================================================
void SortBySbjct(void)
{
	switch ( SelecOfSortBySbjct() ) {

	case SORT_BY_CA :   // By CA
						SortByCA();
						break;

	case SORT_BY_EX:    // By Exam
						SortByExam();
						break;
	}
}

//============================================================================================================================
//============================================         End File         ======================================================
//============================================================================================================================


