#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <dos.h>
#include <dir.h>
#include <conio.h>
#include <time.h>
#define name_len 80
#include"main_game_functions.h"
int x=0,y=0,t=0,num_chosen=0,comp=0,undone=0; //(x,y) are variables used for the position,num_chosen is number of completely used points.
int ch_hor[36]= {0},ch_ver[36]= {0},chosen[36]= {0}; //arrays containing values corresponding to the chosen points.
int line_hor=0,line_ver=0;//number of horizontal and vertical lines drawn.
int i=0;//iteration for turns.
player_data players[2];//array containing structures for the players.
time_t start;//used to call current time.
turn turns[60];
FILE *file;
char check_file_name[80];
int check_file_score;
int number_of_lines=1;
int easy=1,saved=0;


//basic functions.

void Position(int X, int Y)//function takes (x,y) arg. and places the cursor accordingly (x no. of spaces,y no. of new lines).
{
    HANDLE Screen;
    Screen = GetStdHandle(STD_OUTPUT_HANDLE);
    COORD Position= {X, Y};

    SetConsoleCursorPosition(Screen, Position);
}
void set_color(int ForgC)//function sets the text color.
{
    WORD wColor;

    HANDLE hStdOut = GetStdHandle(STD_OUTPUT_HANDLE);
    CONSOLE_SCREEN_BUFFER_INFO csbi;

    if(GetConsoleScreenBufferInfo(hStdOut, &csbi))
    {

        wColor = (csbi.wAttributes & 0xF0) + (ForgC & 0x0F);
        SetConsoleTextAttribute(hStdOut, wColor);
    }
    return;
}
void display_current()//displays all the current data(turn,moves,scores...)
{

    Position(25,1);
    if(i)
    {
        set_color(players[i].color);
        printf("Turn:BLUE");
    }
    else
    {
        set_color(players[i].color);
        printf("Turn:RED  ");
    }
    set_color(15);
    Position(35,1);
    printf("SCORES:          Moves:");
    Position(35,2);
    set_color(1);
    printf("%d                %d   ",players[1].score,players[1].moves);
    Position(40,2);
    set_color(4);
    printf("%d",players[0].score);
    Position(58,2);
    printf("%d",players[0].moves);
}


void reset_all(int r[],int a[],int b[],int k)//if the player plays again all variables are reset and functions are called again.
{
    players[0].moves=0;
    players[1].moves=0;
    players[1].score=0;
    players[0].score=0;
    line_hor=0;
    line_ver=0;
    num_chosen=0;
    t=0;
    i=0;
    x=0;
    y=0;
    undone=0;
    int c;
    for(c=0; c<k; c++)
    {
        r[c]=0;
        a[c]=0;
        b[c]=0;
        turns[c].last_drawn=0;
          turns[c].value_drawn=0;
    turns[c].current_turn=0;
    turns[c].value_square[0]=0;
    turns[c].value_square[1]=0;
    turns[c].passed=0;

    }
}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------//
//functions for the expert game mode.


void play_expert()
{

    players[0].color=4;
    players[1].color=1;
    drawshape_expert();
    set_color(15);
    Position(75,1);
    printf("00 : 00");
    start=clock();
    while(line_hor+line_ver!=60)
    {

        display_current();
        players_expert();
        Point_expert(ch_hor,ch_ver);

    }

    display_current();
    display_results_expert();
}
void players_expert()
{


    int c;
    char input1,input2;
    Position(4*x,2*y);
    switch(getch())
    {
     case 'q':
    {
        set_color(15);
        Position(0,14);
        printf("1-save file(1)\n2-save file(2)\n3-save file(3)\n4-menu\n5-exit");

        do{
        switch(input2=getch())
        {
        case '1':
        {
            save1();
            break;
        }
        case '2':
        {
            save2();
            break;
        }
        case '3':
            {save3();
            break;
            }
        case '4':
            {
                full_game();
                break;
            }
         case '5':
            {exit(0);
            break;
            }

        }
    }while(input2!='1'&&input2!='2'&&input2!='3'&&input2!='4'&&input2!='5');
        Position(0,20);

        printf("e:exit,m:menu");
        do{
        switch(input1=getch())
        {
        case 'e':
        {
            exit(0);
            break;
        }
        case 'm':
        {

            set_color(7);
            full_game();
            break;
        }
        }
    }while(input1!='e'&&input1!='m');
    }
    case'a':
    {
        x--;
        break;
    }
    case 'd':
    {

        x++;

        break;
    }
    case 'w':
    {
        y--;
        break;
    }
    case 's':
    {
        y++;
        break;
    }
    case ' ':
    {
        Position(0,14);
        printf("                           ");
        Position(4*x+1,2*y);
        if(check_expert_hor(x, y,ch_hor)&&x<5)
        {
            set_color(players[i].color);
            printf("%c%c%c",-51,-51,-51);
            Timer(start);
            players[i].moves++;
            turns[t].last_drawn=0;
            turns[t].value_drawn=ch_hor[line_hor];
            turns[t].current_turn=i;
            for(c=0; c<=36; c++)
                turns[c].passed=0;

            t++;
            line_hor++;
            i=!i;
        }
        else
        {
            set_color(15);
            Position(0,14);
            if(x==5)
                printf("Out of range.   ");
            else
                printf("Line was chosen.");
        }
        break;

    }
    case '\t':
    {
        Position(0,14);
        printf("                           ");
        Position(4*x,2*y+1);
        if(check_expert_ver(x, y,ch_ver)&&y<5)
        {
            set_color(players[i].color);
            printf("%c",-70);
            Timer(start);
            players[i].moves++;
            turns[t].last_drawn=1;
            turns[t].value_drawn=ch_ver[line_ver];
            turns[t].current_turn=i;
            for(c=0; c<=36; c++)
                turns[c].passed=0;

            t++;
            line_ver++;
            i=!i;
        }
        else
        {
            set_color(15);
            Position(0,14);
            if(y==5)
                printf("Out of range.   ");
            else
                printf("Line was chosen.");
        }
        break;


    }
    case 'u':
    {
        undo();

        break;
    }
    case 'r':
    {
        redo();
        break;
    }
    }
    if(x>=0&&y>=0&&y<=5&&x<=5)
    {
        Position(4*x,2*y);
    }
    else
    {

        x=0;
        y=0;
        Position(x,y);
    }


}
int check_expert_hor(int x,int y,int A[])
{
    int yes=1,i=0;
    for(i=0; i<line_hor; i++)
    {
        if(x+10*y+1==A[i])
            yes=0;
    }
    if(line_hor<=30&&yes)
        A[line_hor]=x+10*y+1;
    return yes;
}
int check_expert_ver(int x,int y,int A[])
{
    int yes=1,i=0;
    for(i=0; i<line_ver; i++)
    {
        if(x+10*y+1==A[i])
            yes=0;
    }
    if(line_ver<=30&&yes)
        A[line_ver]=x+10*y+1;
    return yes;
}
void drawshape_expert()
{
    int i,j;
    set_color(10);
    for(i=0; i<12; i+=2)
    {
        for(j=0; j<24; j+=4)
        {
            Position(j,i);
            printf("%c",-2);
        }

    }
}


void Point_expert(int ch_hor[],int ch_ver[] )
{
    int c1,c2,c3,c4,c5,a,b,keep=!i,found1,found2,found3,found4=0,no=0;


    for(c1=0; c1<line_hor; c1++)
    {
        found1=0,found2=0,found3=0;
        for(c2=0; c2<num_chosen; c2++)
            if(ch_hor[c1]==chosen[c2])
            {

                found1=1;
                break;
            }
        if(!found1)
        {
            for(c3=0; c3<line_ver; c3++)
                if(ch_hor[c1]==ch_ver[c3])
                {
                    found2=1;
                    break;
                }
            if(found2)
            {
                for(c4=0; c4<line_hor; c4++)
                    if(ch_hor[c4]==ch_hor[c1]+10)
                    {

                        found3=1;
                        break;
                    }

            }
            if (found3)
                for(c5=0; c5<line_ver; c5++)
                    if(ch_ver[c5]==ch_hor[c1]+1)
                    {
                        chosen[num_chosen]=ch_hor[c1];
                        turns[t-1].value_square[no++]=chosen[num_chosen];
                        num_chosen++;
                        players[!i].score++;
                        found4=1;
                        for(a=0; a<=5; a++)
                        {
                            for(b=0; b<=5; b++)
                                if(a+10*b+1==ch_hor[c1])
                                {
                                    Position(4*a+2,2*b+1);
                                    set_color(players[keep].color);
                                    printf("X");
                                }
                        }
                        break;
                    }



        }
    }
    if(found4)
        i=!i;
}

void display_results_expert ()
{
    char input1,input2;
    Position(8,14);
    if(players[0].score>players[1].score)
    {
        set_color(4);
        printf("%s is the winner!!",players[0].Name);

    }
    else if(players[0].score<players[1].score)
    {
        set_color(1);
        printf("%s is the winner!!",players[1].Name);
    }
    else
    {
        set_color(2);
        printf("Draw.");
    }
    Position(0,17);
    set_color(15);
    printf("Play agian? (y:yes ,n:no)");
    do
    {
        switch(input1=getch())
        {
        case 'y':
        {
            system("cls");
            reset_all(ch_ver,ch_hor,chosen,36);
            /*start=clock();*/
            if(comp)

                playvscomp_expert();

            else
                play_expert();
            break;
        }
        case 'n':
        {
            Position(0,18);
            printf("e:exit m:menu");
            do
            {
                switch (input2=getch())
                {
                case 'e':
                {


                    return;
                    break;
                }
                case 'm':
                {
                    reset_all(ch_ver,ch_hor,chosen,36);
                    comp=0;
                    set_color(7);
                    full_game();
                    break;
                }


                }

            }
            while(input2!='e'&&input2!='m');
        }

        }


    }
    while(input1!='y'&&input1!='n');
}
//expert against computer.
void playvscomp_expert()
{
    comp=1;
    srand(time(NULL));
    players[0].color=4;
    players[1].color=1;
    drawshape_expert();
    set_color(15);
    Position(75,1);
    printf("00 : 00");
    start=clock();
    while(line_hor+line_ver!=60)
    {
        display_current();
        playervscomp_expert();
        Point_expert(ch_hor,ch_ver);
        /*if(i)
        Remain();*/   //still working on it.
    }

    display_current();
    display_results_expert();
}
void playervscomp_expert()
{


    int c;
    char input1,input2;
    Position(4*x,2*y);
    if(i)
    {
        switch(getch())
        {
         case 'q':
    {
        set_color(15);
        Position(0,14);
        printf("1-save file(1)\n2-save file(2)\n3-save file(3)\n4-menu\n5-exit");

        do{
        switch(input2=getch())
        {
        case '1':
        {
            save1();
            break;
        }
        case '2':
        {
            save2();
            break;
        }
        case '3':
            {save3();
            break;
            }
        case '4':
            {
                full_game();
                break;
            }
         case '5':
            {exit(0);
            break;
            }

        }
    }while(input2!='1'&&input2!='2'&&input2!='3'&&input2!='4'&&input2!='5');
        Position(0,20);

        printf("e:exit,m:menu");
        do{
        switch(input1=getch())
        {
        case 'e':
        {
            exit(0);
            break;
        }
        case 'm':
        {

            set_color(7);
            full_game();
            break;
        }
        }
    }while(input1!='e'&&input1!='m');
    }
        case'a':
        {
            x--;
            break;
        }
        case 'd':
        {

            x++;

            break;
        }
        case 'w':
        {
            y--;
            break;
        }
        case 's':
        {
            y++;
            break;
        }
        case ' ':
        {
            Position(0,14);
            printf("                           ");
            Position(4*x+1,2*y);
            if(check_expert_hor(x, y,ch_hor)&&x<5)
            {
                set_color(players[i].color);
                printf("%c%c%c",-51,-51,-51);
                Timer(start);
                players[i].moves++;
                turns[t].last_drawn=0;
                turns[t].value_drawn=ch_hor[line_hor];
                turns[t].current_turn=i;
                for(c=0; c<=36; c++)
                    turns[c].passed=0;

                t++;
                line_hor++;
                i=!i;
            }
            else
            {
                set_color(15);
                Position(0,14);
                if(x==5)
                    printf("Out of range.   ");
                else
                    printf("Line was chosen.");
            }
            break;

        }
        case '\t':
        {
            Position(0,14);
            printf("                           ");
            Position(4*x,2*y+1);
            if(check_expert_ver(x, y,ch_ver)&&y<5)
            {
                set_color(players[i].color);
                printf("%c",-70);
                Timer(start);
                players[i].moves++;
                turns[t].last_drawn=1;
                turns[t].value_drawn=ch_ver[line_ver];
                turns[t].current_turn=i;
                for(c=0; c<=36; c++)
                    turns[c].passed=0;

                t++;
                line_ver++;
                i=!i;
            }
            else
            {
                set_color(15);
                Position(0,14);
                if(y==5)
                    printf("Out of range.   ");
                else
                    printf("Line was chosen.");
            }
            break;

        }
        case 'u':
        {
            undo();
            break;
        }
        case 'r':
        {
            redo();
            break;
        }
        }
    }
    else
    {
        comp_expert_random();
    }
    if(x>=0&&y>=0&&y<=5&&x<=5)
    {
        Position(4*x,2*y);
    }
    else
    {

        x=0;
        y=0;
        Position(x,y);
    }


}

void comp_expert_random()//computer plays chosing random points.
{
    do

    {

        int r;

        x=rand()%6;//has only 6 possible values 0,1,2,3,4,5.
        y=rand()%6;
        Position(4*x,2*y);
        r=rand()%2;//can be 0 or 1.

        /*if(remain)
        {
           x=x_ai;
           y=y_ai;
        }*/   //still working on it.
        switch(r)
        {
        case 0://draw horizontal.
        {
            Position(0,14);
            printf("                           ");
            Position(4*x+1,2*y);
            if(check_expert_hor(x, y,ch_hor)&&x<5)
            {
                set_color(players[i].color);
                printf("%c%c%c",-51,-51,-51);
                players[i].moves++;
                turns[t].last_drawn=0;
                turns[t].value_drawn=ch_hor[line_hor];
                t++;
                line_hor++;
                i=!i;
            }
            break;

        }
        case 1://draw vertical.
        {


            Position(0,14);
            printf("                           ");
            Position(4*x,2*y+1);
            if(check_expert_ver(x, y,ch_ver)&&y<5)
            {
                set_color(players[i].color);
                printf("%c",-70);
                players[i].moves++;
                turns[t].last_drawn=1;
                turns[t].value_drawn=ch_ver[line_ver];
                t++;
                line_ver++;
                i=!i;
            }
            break;
        }
        }

    }

    while(!i);
}
//------------------------------------------------------------------------------------------------------------------------------------------------------------//
//functions for the easy game mode.

void play_easy()//function for human vs human,easy.
{

    players[0].color=4;
    players[1].color=1;
    drawshape_easy();
    set_color(15);
    Position(75,1);
    printf("00 : 00");
    start=clock();
    while(line_hor+line_ver!=12)
    {


        display_current();
        players_easy();
        Point_easy(ch_hor,ch_ver);

    }

    display_current();
    display_results_easy();
    Position(0,18);



}

void players_easy()//for playing (game flow) of 2 players easy.
{



    int c;
    char input1,input2;
    Position(4*x,2*y);
    switch(getch()) //method to get input from the user where acc. to the input the user either navigates or draws a line.
    {

    case 'q':
    {
        set_color(15);
        Position(0,14);
        printf("1-save file(1)\n2-save file(2)\n3-save file(3)\n4-menu\n5-exit");

        do{
        switch(input2=getch())
        {
        case '1':
        {
            save1();
            break;
        }
        case '2':
        {
            save2();
            break;
        }
        case '3':
            {save3();
            break;
            }
        case '4':
            {
                full_game();
                break;
            }
         case '5':
            {exit(0);
            break;
            }

        }
    }while(input2!='1'&&input2!='2'&&input2!='3'&&input2!='4'&&input2!='5');
        Position(0,20);

        printf("e:exit,m:menu");
        do{
        switch(input1=getch())
        {
        case 'e':
        {
            exit(0);
            break;
        }
        case 'm':
        {

            set_color(7);
            full_game();
            break;
        }
        }
    }while(input1!='e'&&input1!='m');
    }
    case'a':
    {
        x--;
        break;
    }
    case 'd':
    {

        x++;

        break;
    }
    case 'w':
    {
        y--;
        break;
    }
    case 's':
    {
        y++;
        break;
    }
    case ' ':
    {
        Position(0,8);
        printf("                           ");
        Position(4*x+1,2*y);
        if(check_easy_hor(x, y,ch_hor)&&x<2)
        {
            set_color(players[i].color);
            printf("%c%c%c",-51,-51,-51);
            players[i].moves++;
            Timer(start);
            turns[t].last_drawn=0;
            turns[t].value_drawn=ch_hor[line_hor];
            turns[t].current_turn=i;
            for(c=0; c<=36; c++)
                turns[c].passed=0;
            t++;
            line_hor++;
            i=!i;
        }
        else
        {
            set_color(15);
            Position(0,8);
            if(x==2)//at rightmost point.
                printf("Out of range.   ");
            else
                printf("Line was chosen.");
        }
        break;

    }
    case '\t':
    {
        Position(0,8);
        printf("                           ");
        Position(4*x,2*y+1);
        if(check_easy_ver(x, y,ch_ver)&&y<2)
        {
            set_color(players[i].color);
            printf("%c",-70);
            Timer(start);
            players[i].moves++;
            turns[t].last_drawn=1;
            turns[t].value_drawn=ch_ver[line_ver];
            turns[t].current_turn=i;
            for(c=0; c<=36; c++)
                turns[c].passed=0;
            t++;
            line_ver++;
            i=!i;
        }
        else
        {
            set_color(15);
            Position(0,8);
            if(y==2)//at bottom point.
                printf("Out of range.   ");
            else
                printf("Line was chosen.");
        }
        break;

    }
    case 'u':
    {
        undo();
        break;
    }
    case 'r':
    {
        redo();
        break;
    }
    }
    if(x>=0&&y>=0&&y<=2&&x<=2)//in the allowed range.
    {
        Position(4*x,2*y);
    }
    else
    {

        x=0;
        y=0;
        Position(x,y);
    }


}
int check_easy_hor(int x,int y,int A[])//function checks whether a horizontal line was drawn from this point.
{
    int yes=1,i=0;
    for(i=0; i<line_hor; i++)
    {
        if(x+10*y+1==A[i])//this is an equation to obtain a certain value for each point.
            yes=0;
    }
    if(line_hor<=6&&yes)
        A[line_hor]=x+10*y+1;
    return yes;
}
int check_easy_ver(int x,int y,int A[])//function checks whether a vertical line was drawn from this point.
{
    int yes=1,i=0;
    for(i=0; i<line_ver; i++)
    {
        if(x+10*y+1==A[i])//this is an equation to obtain a certain value for each point.
            yes=0;
    }
    if(line_ver<=6&&yes)
        A[line_ver]=x+10*y+1;
    return yes;
}
void drawshape_easy()//draws the game design for easy.
{
    int i,j;
    set_color(10);
    for(i=0; i<6; i+=2)
    {
        for(j=0; j<12; j+=4)
        {
            Position(j,i);
            printf("%c",-2);
        }

    }
    Position(0,0);
}



void Point_easy(int ch_hor[],int ch_ver[] )//checks whether a square was completed and if so updates the scores.
{
    int c1,c2,c3,c4,c5,a,b,keep=!i,found1,found2,found3,found4=0,no=0;


    for(c1=0; c1<line_hor; c1++)//loop in ch_hor (points from which horizontal lines were drawn).
    {
        found1=0,found2=0,found3=0;
        for(c2=0; c2<num_chosen; c2++)//check that this square wasn't previously chosen.
            if(ch_hor[c1]==chosen[c2])
            {

                found1=1;
                break;
            }
        if(!found1)
        {
            for(c3=0; c3<line_ver; c3++)//loop in ch_ver to find whether a vertical line was also drawn from the point.
                if(ch_hor[c1]==ch_ver[c3])
                {
                    found2=1;
                    break;
                }
            if(found2)
            {
                for(c4=0; c4<line_hor; c4++)//check that a horizontal line was drawn form the point below it.
                    if(ch_hor[c4]==ch_hor[c1]+10)
                    {

                        found3=1;
                        break;
                    }

            }
            if (found3)
                for(c5=0; c5<line_ver; c5++)//check that a vertical line was drawn from the point to its right.
                    if(ch_ver[c5]==ch_hor[c1]+1)
                    {
                        chosen[num_chosen]=ch_hor[c1];
                        turns[t-1].value_square[no++]=chosen[num_chosen];
                        num_chosen++;
                        players[!i].score++;
                        found4=1;
                        for(a=0; a<=1; a++)//a loop to get the center of the square and indicate which player closed it.
                        {
                            for(b=0; b<=1; b++)
                                if(a+10*b+1==ch_hor[c1])
                                {
                                    Position(4*a+2,2*b+1);
                                    set_color(players[keep].color);
                                    printf("X");
                                }
                        }
                        break;
                    }



        }
    }
    if(found4)//keep the turn of the one who closed the square
        i=!i;
}
void display_results_easy ()//displays the winner after the game ends.
{
    char input1,input2;
    Position(8,6);
    if(players[0].score>players[1].score)
    {
        set_color(4);
        printf("%s is the winner!!",players[0].Name);

    }
    else if(players[0].score<players[1].score)
    {
        set_color(1);
        printf("%s is the winner!!",players[1].Name);
    }
    else
    {
        set_color(2);
        printf("Draw.");
    }
    Position(0,7);
    set_color(15);
    printf("Play agian? (y:yes ,n:no)");//obtain input whether the player wants to play again or exit.
    do
    {
        switch(input1=getch())
        {
        case 'y':
        {
            system("cls");
            reset_all(ch_hor,ch_ver,chosen,9);
            /* start=clock();*/
            if(comp)
                playvscomp_easy();
            else
                play_easy();
            break;
        }
        case 'n':
        {
            Position(0,8);
            printf("e:exit m:menu");
            do
            {
                switch (input2=getch())
                {
                case 'e':
                {

                    return;
                    break;
                }


                case 'm':

                {
                    reset_all(ch_hor,ch_ver,chosen,9);
                    comp=0;
                    set_color(7);
                    full_game();
                    break;

                }
            }
            }
            while(input2!='e'&&input2!='m');
        }

        }


    }
    while(input1!='y'&&input1!='n');
}

//for easy vs computer.



void playvscomp_easy()//function for human vs comp,easy.
{
    comp=1;
    srand(time(NULL));
    players[0].color=4;
    players[1].color=1;
    drawshape_easy();
    set_color(15);
    Position(75,1);
    printf("00 : 00");
    start=clock();
    while(line_hor+line_ver!=12)
    {


        display_current();
        playervscomp_easy();

        Point_easy(ch_hor,ch_ver);

    }

    display_current();
    display_results_easy();
    Position(0,18);



}

void playervscomp_easy()
{


    int c;
    char input1,input2;
    Position(4*x,2*y);
    if(i)
    {
        switch(getch()) //method to get input from the user where acc. to the input the user either navigates or draws a line.
        {
         case 'q':
    {
        set_color(15);
        Position(0,14);
        printf("1-save file(1)\n2-save file(2)\n3-save file(3)\n4-menu\n5-exit");

        do{
        switch(input2=getch())
        {
        case '1':
        {
            save1();
            break;
        }
        case '2':
        {
            save2();
            break;
        }
        case '3':
            {save3();
            break;
            }
        case '4':
            {
                full_game();
                break;
            }
         case '5':
            {exit(0);
            break;
            }

        }
    }while(input2!='1'&&input2!='2'&&input2!='3'&&input2!='4'&&input2!='5');
        Position(0,20);

        printf("e:exit,m:menu");
        do{
        switch(input1=getch())
        {
        case 'e':
        {
            exit(0);
            break;
        }
        case 'm':
        {

            set_color(7);
            full_game();
            break;
        }
        }
    }while(input1!='e'&&input1!='m');
    }
        case'a':
        {
            x--;
            break;
        }
        case 'd':
        {

            x++;

            break;
        }
        case 'w':
        {
            y--;
            break;
        }
        case 's':
        {
            y++;
            break;
        }
        case ' ':
        {
            Position(0,8);
            printf("                           ");
            Position(4*x+1,2*y);
            if(check_easy_hor(x, y,ch_hor)&&x<2)
            {
                set_color(players[i].color);
                printf("%c%c%c",-51,-51,-51);
                players[i].moves++;
                Timer(start);
                turns[t].last_drawn=0;
                turns[t].value_drawn=ch_hor[line_hor];
                turns[t].current_turn=i;
                for(c=0; c<=36; c++)
                    turns[c].passed=0;

                t++;
                line_hor++;
                i=!i;
            }
            else
            {
                set_color(15);
                Position(0,8);
                if(x==2)//at rightmost point.
                    printf("Out of range.   ");
                else
                    printf("Line was chosen.");
            }
            break;

        }
        case '\t':
        {
            Position(0,8);
            printf("                           ");
            Position(4*x,2*y+1);
            if(check_easy_ver(x, y,ch_ver)&&y<2)
            {
                set_color(players[i].color);
                printf("%c",-70);
                Timer(start);
                players[i].moves++;
                turns[t].last_drawn=1;
                turns[t].value_drawn=ch_ver[line_ver];
                turns[t].current_turn=i;
                for(c=0; c<=36; c++)
                    turns[c].passed=0;

                t++;
                line_ver++;
                i=!i;
            }
            else
            {
                set_color(15);
                Position(0,8);
                if(y==2)//at bottom point.
                    printf("Out of range.   ");
                else
                    printf("Line was chosen.");
            }
            break;

        }
        case 'u':
        {
            undo();
            break;
        }
        case 'r':
        {
            redo();
            break;
        }
        }
    }
    else//computers turn
    {
        comp_easy_random();
    }
    if(x>=0&&y>=0&&y<=2&&x<=2)//in the allowed range.
    {
        Position(4*x,2*y);
    }
    else
    {

        x=0;
        y=0;
        Position(x,y);
    }


}
void comp_easy_random()//computer plays chosing random points.
{
    do

    {

        int r;

        x=rand()%3;//has only 3 possible values 0,1,2.
        y=rand()%3;
        Position(4*x,2*y);
        r=rand()%2;//can be 0 or 1.

        switch(r)
        {
        case 0:
        {
            Position(0,8);
            printf("                           ");
            Position(4*x,2*y+1);
            if(check_easy_ver(x, y,ch_ver)&&y<2)
            {
                set_color(players[i].color);
                printf("%c",-70);
                players[i].moves++;
                turns[t].last_drawn=1;
                turns[t].value_drawn=ch_ver[line_ver];
                turns[t].current_turn=i;
                t++;
                line_ver++;
                i=!i;
            }
            break;

        }
        case 1:
        {


            Position(0,8);
            printf("                           ");
            Position(4*x+1,2*y);
            if(check_easy_hor(x, y,ch_hor)&&x<2)
            {
                set_color(players[i].color);
                printf("%c%c%c",-51,-51,-51);
                players[i].moves++;
                turns[t].last_drawn=0;
                turns[t].value_drawn=ch_hor[line_hor];
                turns[t].current_turn=i;
                t++;
                line_hor++;
                i=!i;
            }
            break;
        }
        }

    }

    while(!i);
}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------------//

//The full game function:

void full_game()
{

    reset_all(ch_hor,ch_ver,chosen,60);
    players[0].color=4;
    players[1].color=1;
    system("cls");
    char input1,input2,input3,input4;
    printf("1-Start Game\n2-Load Game\n3-Top 10 Players\n4-Exit\n");
    do
    {
        switch(input1=getch())
        {
        case '1' :
        {
            system("cls");
            printf("1-Beginner\n2-Expert\n3-Menu\n");
            do
            {

                switch(input2=getch())
                {
                case '1' :
                {
                    system("cls");
                    printf("1-Human vs Human\n2-Human vs Computer\n3-Menu\n");
                    do
                    {
                        switch(input3=getch())
                        {
                        case '1' :
                        {
                            system("cls");
                            printf("Enter 1st Player Name : ");
                            gets(players[0].Name);
                            printf("\n");
                            printf("Enter 2nd Player Name : ");
                            gets(players[1].Name);
                            system("cls");
                            play_easy();
                            break;
                        }
                        case '2':
                        {
                            system("cls");
                            printf("Enter your Name : ");
                            gets(players[1].Name);
                            printf("\n");
                            strcpy(players[0].Name,"computer");
                            system("cls");
                            playvscomp_easy();
                            break;
                        }
                        case '3' :
                        {
                            system("cls");
                            full_game();
                            break;
                        }
                        }
                    }
                    while(input3!='1'&&input3!='2'&&input3!='3');
                    break;
                }
                case '2' :
                {
                    system("cls");
                    easy=0;
                    printf("1-Human vs Human\n2-Human vs Computer\n3-Menu\n");
                    do
                    {
                        switch(input3=getch())
                        {
                        case '1' :
                        {
                            system("cls");
                            printf("Enter 1st Player Name : ");
                            gets(players[0].Name);
                            printf("\n");
                            printf("Enter 2nd Player Name : ");
                            gets(players[1].Name);
                            system("cls");
                            play_expert();
                            break;
                        }
                        case '2':
                        {
                            system("cls");
                            printf("Enter your Name : ");
                            gets(players[1].Name);
                            printf("\n");
                            strcpy(players[0].Name,"computer");
                            system("cls");
                            playvscomp_expert();
                            break;
                        }
                        case '3' :
                        {
                            system("cls");
                            full_game();
                            break;
                        }
                        }
                    }
                    while(input3!='1'&&input3!='2'&&input3!='3');
                    break;
                }
                case '3' :
                {
                    system("cls");
                    full_game();
                    break;
                }
                }
            }
            while(input2!='1'&&input2!='2'&&input2!='3');
            break;
        }
        case '2' :
        {

            system("cls");
            printf("1-saved game(1)\n2-saved game(2)\n3-saved game(3)\n4-menu");
         do
         {


            switch(input4=getch())
           {
            case '1':
        {
           load1();
           break;
        }
            case '2':
                {
                    load2();
                    break;
                }
            case '3':
                {
                    load3();
                    break;
                }
            case '4':
                {
                    full_game();
                    break;
                }


           }
        }while(input4!='1'&&input4!='2'&&input4!='3'&&input4!='4');
        }
        case '3' :
        {
            system("cls");
            printf("1-Beginner\n2-Expert\n3-Menu\n");
            do
            {
                switch(input2=getch())
                {
                case '1' :
                {
                    system("cls");
                    printf("1-Human vs Human\n2-Human vs Computer\n3-Menu\n");
                    do
                    {
                        switch(input3=getch())
                        {
                        case '1' :
                        {
                            system("cls");
                            file=fopen("Top 10_easy_pvsp.txt","r");
                            print_top_10();
                            fclose(file);

                            char input4;
                            Position(30,0);
                            printf("Press any key to return to Menu.");
                            input4=getch();
                            full_game();
                            break;
                        }
                        case '2' :
                        {
                            system("cls");
                            file=fopen("Top 10_easy_pvsc.txt","r");
                            print_top_10();
                            fclose(file);

                            char input4;
                            Position(30,0);
                            printf("Press any key to return to Menu.");
                            input4=getch();
                            full_game();
                            break;
                        }
                        case '3' :
                        {
                            system("cls");
                            full_game();
                            break;
                        }
                        }
                    }
                    while(input3!='1'&&input3!='2'&&input3!='3');
                    break;
                }
                case '2' :
                {
                    system("cls");
                    printf("1-Human vs Human\n2-Human vs Computer\n3-Menu\n");
                    do
                    {
                        switch(input3=getch())
                        {
                        case '1' :
                        {
                            system("cls");
                            file=fopen("Top 10_expert_pvsp.txt","r");
                            print_top_10();
                            fclose(file);

                            char input4;
                            Position(30,0);
                            printf("Press any key to return to Menu.");
                            input4=getch();
                            full_game();
                            break;
                        }
                        case '2' :
                        {
                            system("cls");
                            file=fopen("Top 10_expert_pvsc.txt","r");
                            print_top_10();
                            fclose(file);
                            char input4;
                            Position(30,0);
                            printf("Press any key to return to Menu.");
                            input4=getch();
                            full_game();
                            break;
                        }
                        case '3' :
                        {
                            system("cls");
                            full_game();
                            break;
                        }
                        }
                    }
                    while(input3!='1'&&input3!='2'&&input3!='3');
                    break;
                }
                case '3' :
                {
                    system("cls");
                    full_game();
                    break;
                }
                }
            }
            while(input2!='1'&&input2!='2'&&input2!='3');
            break;
        }
        case '4' :
        {
            return;
            break;
        }
        }
    }
    while(input1!='1'&&input1!='2'&&input1!='3'&&input1!='4');

}

void Timer(time_t start)
{
    time_t end;
    end = clock();
    Position(75,1);
    set_color(15);
    int diff;
    diff = (end - start)/CLOCKS_PER_SEC;
    printf("%02d : %02d",diff/60,diff%60);
}
void undo()
{
    int a,b,no=0;
    if (t!=0)
    {
        t--;
        turns[t].passed=1;


        for(a=0; a<=5; a++)
        {
            for(b=0; b<=5; b++)
                if(a+10*b+1==turns[t].value_drawn)
                {

                    if(turns[t].last_drawn==0)
                    {
                        Position(4*a+1,2*b);
                        printf("   ");
                        ch_hor[line_hor]=0;
                        line_hor--;
                    }
                    else
                    {
                        Position(4*a,2*b+1);
                        printf(" ");
                        ch_ver[line_ver]=0;
                        line_ver--;
                    }
                }
        }

        for(no; no<=1; no++)
        {
            if(turns[t].value_square[!no]==chosen[num_chosen-1]&&turns[t].value_square[!no]!=0)
            {
                for(a=0; a<=5; a++)
                {
                    for(b=0; b<=5; b++)
                        if(a+10*b+1==turns[t].value_square[!no])

                        {
                            Position(4*a+2,2*b+1);
                            printf(" ");
                            players[turns[t].current_turn].score--;
                            chosen[--num_chosen]=0;

                        }
                }

            }
        }

    }


    Position(4*x,2*y);
}
void redo()
{
    int a,b,no=0;
    if(turns[t].passed)
    {



        for(a=0; a<=5; a++)
        {
            for(b=0; b<=5; b++)
                if(a+10*b+1==turns[t].value_drawn)
                {

                    if(turns[t].last_drawn==0)
                    {
                        Position(4*a+1,2*b);
                        set_color(players[turns[t].current_turn].color);
                        printf("%c%c%c",-51,-51,-51);
                        ch_hor[line_hor]=turns[t].value_drawn;
                        check_easy_hor(a, b,ch_hor);
                        line_hor++;
                    }
                    else
                    {
                        Position(4*a,2*b+1);
                        set_color(players[turns[t].current_turn].color);
                        printf("%c",-70);
                        ch_ver[line_ver]=turns[t].value_drawn;
                        check_easy_ver(a, b,ch_ver);
                        line_ver++;
                    }
                }
        }
  if(comp)
            i=1;
        else
            i=!turns[t].current_turn;

        t++;
    }


    Position(4*x,2*y);
}



//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//High scores and save functions.

int lines_in_file()
{
    int c1=2;
    char X[80];

    while(fgets(X,sizeof(X),file))
        c1++;

    return c1;
}


void high_scores_easy_pvsp(char rank_name[],int rank_score)
{
    strlwr(rank_name);
    int flag,c2,c1,c3,found=0,size;
    file=fopen("Top 10_easy_pvsp.txt","a+");
    size=lines_in_file()/2;
    rewind(file);
    leader_board names[size];
    leader_board temp[size];
    for (c2=0 ; !feof(file) ; c2++)
    {
        fscanf(file,"%s\n%d\n",names[c2].name,&names[c2].score);
        if(!strcmp(rank_name,names[c2].name))
        {
            if(rank_score>names[c2].score)

                names[c2].score=rank_score;



            found=1;
        }
    }
    if(!found)
    {
        names[size-1].score=rank_score;
        strcpy(names[size-1].name,rank_name);
    }
    fclose(file);
    for (c1=0 ; c1<size-1-found ; c1++)
    {
        for (c3=c1+1 ; c3<size-found ; c3++)
        {
            if (names[c3].score>names[c1].score)
            {
                strcpy(temp[c1].name,names[c1].name);
                temp[c1].score=names[c1].score;
                strcpy(names[c1].name,names[c3].name);
                names[c1].score=names[c3].score;
                strcpy(names[c3].name,temp[c1].name);
                names[c3].score=temp[c1].score;
            }
        }
    }
    file=fopen("Top 10_easy_pvsp.txt","w");

    rewind(file);
    for (c2=0 ; c2<size&&names[c2].score<=25 ; c2++)
    {
        fprintf(file,"%s\n%d\n",names[c2].name,names[c2].score);
    }
    fclose(file);
}


void high_scores_easy_pvsc(char rank_name[],int rank_score)
{
    strlwr(rank_name);
    int flag,c2,c1,c3,found=0,size;
    file=fopen("Top 10_easy_pvsc.txt","a+");
    size=lines_in_file()/2;
    rewind(file);
    leader_board names[size];
    leader_board temp[size];
    for (c2=0 ; !feof(file) ; c2++)
    {
        fscanf(file,"%s\n%d\n",names[c2].name,&names[c2].score);
        if(!strcmp(rank_name,names[c2].name))
        {
            if(rank_score>names[c2].score)

                names[c2].score=rank_score;



            found=1;
        }
    }
    if(!found)
    {
        names[size-1].score=rank_score;
        strcpy(names[size-1].name,rank_name);
    }
    fclose(file);
    for (c1=0 ; c1<size-1-found ; c1++)
    {
        for (c3=c1+1 ; c3<size-found ; c3++)
        {
            if (names[c3].score>names[c1].score)
            {
                strcpy(temp[c1].name,names[c1].name);
                temp[c1].score=names[c1].score;
                strcpy(names[c1].name,names[c3].name);
                names[c1].score=names[c3].score;
                strcpy(names[c3].name,temp[c1].name);
                names[c3].score=temp[c1].score;
            }
        }
    }
    file=fopen("Top 10_easy_pvsc.txt","w");

    rewind(file);
    for (c2=0 ; c2<size&&names[c2].score<=25 ; c2++)
    {
        fprintf(file,"%s\n%d\n",names[c2].name,names[c2].score);
    }
    fclose(file);
}


void high_scores_expert_pvsp(char rank_name[],int rank_score)
{
    strlwr(rank_name);
    int flag,c2,c1,c3,found=0,size;
    file=fopen("Top 10_expert_pvsp.txt","a+");
    size=lines_in_file()/2;
    rewind(file);
    leader_board names[size];
    leader_board temp[size];
    for (c2=0 ; !feof(file) ; c2++)
    {
        fscanf(file,"%s\n%d\n",names[c2].name,&names[c2].score);
        if(!strcmp(rank_name,names[c2].name))
        {
            if(rank_score>names[c2].score)

                names[c2].score=rank_score;



            found=1;
        }
    }
    if(!found)
    {
        names[size-1].score=rank_score;
        strcpy(names[size-1].name,rank_name);
    }
    fclose(file);
    for (c1=0 ; c1<size-1-found ; c1++)
    {
        for (c3=c1+1 ; c3<size-found ; c3++)
        {
            if (names[c3].score>names[c1].score)
            {
                strcpy(temp[c1].name,names[c1].name);
                temp[c1].score=names[c1].score;
                strcpy(names[c1].name,names[c3].name);
                names[c1].score=names[c3].score;
                strcpy(names[c3].name,temp[c1].name);
                names[c3].score=temp[c1].score;
            }
        }
    }
    file=fopen("Top 10_expert_pvsp.txt","w");

    rewind(file);
    for (c2=0 ; c2<size&&names[c2].score<=25 ; c2++)
    {
        fprintf(file,"%s\n%d\n",names[c2].name,names[c2].score);
    }
    fclose(file);
}


void high_scores_expert_pvsc(char rank_name[],int rank_score)
{
    strlwr(rank_name);
    int flag,c2,c1,c3,found=0,size;
    file=fopen("Top 10_expert_pvsc.txt","a+");
    size=lines_in_file()/2;
    rewind(file);
    leader_board names[size];
    leader_board temp[size];
    for (c2=0 ; !feof(file) ; c2++)
    {
        fscanf(file,"%s\n%d\n",names[c2].name,&names[c2].score);
        if(!strcmp(rank_name,names[c2].name))
        {
            if(rank_score>names[c2].score)

                names[c2].score=rank_score;



            found=1;
        }
    }
    if(!found)
    {
        names[size-1].score=rank_score;
        strcpy(names[size-1].name,rank_name);
    }
    fclose(file);
    for (c1=0 ; c1<size-1-found ; c1++)
    {
        for (c3=c1+1 ; c3<size-found ; c3++)
        {
            if (names[c3].score>names[c1].score)
            {
                strcpy(temp[c1].name,names[c1].name);
                temp[c1].score=names[c1].score;
                strcpy(names[c1].name,names[c3].name);
                names[c1].score=names[c3].score;
                strcpy(names[c3].name,temp[c1].name);
                names[c3].score=temp[c1].score;
            }
        }
    }
    file=fopen("Top 10_expert_pvsc.txt","w");

    rewind(file);
    for (c2=0 ; c2<size&&names[c2].score<=25 ; c2++)
    {

        fprintf(file,"%s\n%d\n",names[c2].name,names[c2].score);
    }
    fclose(file);
}


void print_top_10()
{
    char print_name[80];
    int print_score;


    int c4;

    rewind(file);


    Position(10,1);
    set_color(13);
    printf("TOP TEN");
    set_color(7);
    for (c4=0 ; c4<10&&!feof(file) ; c4++)
    {

        fscanf(file,"%s\n%d\n",print_name,&print_score);

        Position(0,c4+3);
        printf("%d- %s",c4+1,print_name);
        Position(25,c4+3);
        printf("%d",print_score);
    }

}


void save1()
{
    saved=1;
    int counter1=0;
    file=fopen("Save1.txt","w");
    rewind(file);
    fprintf(file,"%s\n%s\n%d\n%d\n",players[0].Name,players[1].Name,players[0].moves,players[1].moves);
    fprintf(file,"%d\n%d\n%d\n",t,undone,i);
    for(counter1; counter1<t+undone; counter1++)
    {
        fprintf(file," %d %d %d %d %d %d\n",turns[counter1].last_drawn,turns[counter1].value_drawn,turns[counter1].current_turn,turns[counter1].passed,turns[counter1].value_square[0],turns[counter1].value_square[1]);
    }
    for(counter1=0; counter1<num_chosen; counter1++)
        fprintf(file,"%d\n",chosen[counter1]);

    for(counter1=0; counter1<line_hor; counter1++)
        fprintf(file,"%d\n",ch_hor[counter1]);

    for(counter1=0; counter1<line_ver; counter1++)
        fprintf(file,"%d\n",ch_ver[counter1]);



    fprintf(file,"%d\n%d\n",easy,comp);
    fclose(file);
}
void save2()
{
    saved=1;
    int counter1=0;
    file=fopen("Save2.txt","w");
    rewind(file);
    fprintf(file,"%s\n%s\n%d\n%d\n",players[0].Name,players[1].Name,players[0].moves,players[1].moves);
    fprintf(file,"%d\n%d\n%d\n",t,undone,i);
    for(counter1; counter1<t+undone; counter1++)
    {
        fprintf(file," %d %d %d %d %d %d\n",turns[counter1].last_drawn,turns[counter1].value_drawn,turns[counter1].current_turn,turns[counter1].passed,turns[counter1].value_square[0],turns[counter1].value_square[1]);
    }
    for(counter1=0; counter1<num_chosen; counter1++)
        fprintf(file,"%d\n",chosen[counter1]);

    for(counter1=0; counter1<line_hor; counter1++)
        fprintf(file,"%d\n",ch_hor[counter1]);

    for(counter1=0; counter1<line_ver; counter1++)
        fprintf(file,"%d\n",ch_ver[counter1]);



    fprintf(file,"%d\n%d\n",easy,comp);
    fclose(file);
}
void save3()
{
    saved=1;
    int counter1=0;
    file=fopen("Save3.txt","w");
    rewind(file);
    fprintf(file,"%s\n%s\n%d\n%d\n",players[0].Name,players[1].Name,players[0].moves,players[1].moves);
    fprintf(file,"%d\n%d\n%d\n",t,undone,i);
    for(counter1; counter1<t+undone; counter1++)
    {
        fprintf(file," %d %d %d %d %d %d\n",turns[counter1].last_drawn,turns[counter1].value_drawn,turns[counter1].current_turn,turns[counter1].passed,turns[counter1].value_square[0],turns[counter1].value_square[1]);
    }
    for(counter1=0; counter1<num_chosen; counter1++)
        fprintf(file,"%d\n",chosen[counter1]);

    for(counter1=0; counter1<line_hor; counter1++)
        fprintf(file,"%d\n",ch_hor[counter1]);

    for(counter1=0; counter1<line_ver; counter1++)
        fprintf(file,"%d\n",ch_ver[counter1]);



    fprintf(file,"%d\n%d\n",easy,comp);
    fclose(file);
}

void load1()
{

    reset_all(ch_hor,ch_ver,chosen,36);
    system("cls");
    int a,b,counter1,counter2;
    int z=turns[0].current_turn;
    file=fopen("Save1.txt","r");
    fscanf(file,"%s\n%s\n%d\n%d\n",players[0].Name,players[1].Name,&players[0].moves,&players[1].moves);

    fscanf(file,"%d\n%d\n%d\n",&t,&undone,&i);
    for (counter1=0 ; counter1<t+undone ; counter1++)
    {
        fscanf(file," %d %d %d %d %d %d\n",&turns[counter1].last_drawn,&turns[counter1].value_drawn,&turns[counter1].current_turn,&turns[counter1].passed,&turns[counter1].value_square[0],&turns[counter1].value_square[1]);
    }
    for (counter1=0 ; counter1<t ; counter1++)
    {
        if (turns[counter1].last_drawn)
        {
            for(a=0; a<=5; a++)
            {


                for(b=0; b<=5; b++)
                {
                    if(a+10*b+1==turns[counter1].value_drawn)
                    {
                        Position(4*a,2*b+1);

                    }
                }
            }
            z=turns[counter1].current_turn;
            set_color(players[z].color);
            printf("%c",-70);

            line_ver++;
        }
        else
        {
            for(a=0; a<=5; a++)
            {


                for(b=0; b<=5; b++)
                {
                    if(a+10*b+1==turns[counter1].value_drawn)
                    {
                        Position(4*a+1,2*b);

                    }
                }
            }
            z=turns[counter1].current_turn;
            set_color(players[z].color);
            printf("%c%c%c",-51,-51,-51);

            line_hor++;


        }
    }
    for(counter1=0; counter1<t; counter1++)
    {
        if(turns[counter1].value_square[0]>0)
        {
            for(a=0; a<=5; a++)
            {
                for(b=0; b<=5; b++)
                    if(a+10*b+1==turns[counter1].value_square[0])

                    {
                        Position(4*a+2,2*b+1);
                        set_color(players[turns[counter1].current_turn].color);
                        printf("X");
                        players[turns[counter1].current_turn].score++;
                        num_chosen++;

                    }
            }

        }
        if(turns[counter1].value_square[1]>0)
        {
            for(a=0; a<=5; a++)
            {
                for(b=0; b<=5; b++)
                    if(a+10*b+1==(chosen[counter2++]=turns[counter1].value_square[1]))

                    {
                        Position(4*a+2,2*b+1);
                        set_color(players[turns[counter1].current_turn].color);
                        printf("X");
                        players[turns[counter1].current_turn].score++;
                        num_chosen++;

                    }
            }

        }


    }

    for(counter1=0; counter1<num_chosen; counter1++)
        fscanf(file,"%d\n",&chosen[counter1]);
    for(counter1=0; counter1<line_hor; counter1++)
        fscanf(file,"%d\n",&ch_hor[counter1]);
    for(counter1=0; counter1<line_ver; counter1++)
        fscanf(file,"%d\n",&ch_ver[counter1]);

    fscanf(file,"%d\n%d\n",&easy,&comp);

    fclose(file);

    if(easy&&comp)
        playvscomp_easy();
    else if(easy&&!comp)
        play_easy();
    else if(!easy&&comp)
        playvscomp_expert();
    else if(!easy&&!comp)
        play_expert();
}
void load2()
{

    reset_all(ch_hor,ch_ver,chosen,36);
    system("cls");
    int a,b,counter1,counter2;
    int z=turns[0].current_turn;
    file=fopen("Save2.txt","r");
    fscanf(file,"%s\n%s\n%d\n%d\n",players[0].Name,players[1].Name,&players[0].moves,&players[1].moves);

    fscanf(file,"%d\n%d\n%d\n",&t,&undone,&i);
    for (counter1=0 ; counter1<t+undone ; counter1++)
    {
        fscanf(file," %d %d %d %d %d %d\n",&turns[counter1].last_drawn,&turns[counter1].value_drawn,&turns[counter1].current_turn,&turns[counter1].passed,&turns[counter1].value_square[0],&turns[counter1].value_square[1]);
    }
    for (counter1=0 ; counter1<t ; counter1++)
    {
        if (turns[counter1].last_drawn)
        {
            for(a=0; a<=5; a++)
            {


                for(b=0; b<=5; b++)
                {
                    if(a+10*b+1==turns[counter1].value_drawn)
                    {
                        Position(4*a,2*b+1);

                    }
                }
            }
            z=turns[counter1].current_turn;
            set_color(players[z].color);
            printf("%c",-70);

            line_ver++;
        }
        else
        {
            for(a=0; a<=5; a++)
            {


                for(b=0; b<=5; b++)
                {
                    if(a+10*b+1==turns[counter1].value_drawn)
                    {
                        Position(4*a+1,2*b);

                    }
                }
            }
            z=turns[counter1].current_turn;
            set_color(players[z].color);
            printf("%c%c%c",-51,-51,-51);

            line_hor++;


        }
    }
    for(counter1=0; counter1<t; counter1++)
    {
        if(turns[counter1].value_square[0]>0)
        {
            for(a=0; a<=5; a++)
            {
                for(b=0; b<=5; b++)
                    if(a+10*b+1==turns[counter1].value_square[0])

                    {
                        Position(4*a+2,2*b+1);
                        set_color(players[turns[counter1].current_turn].color);
                        printf("X");
                        players[turns[counter1].current_turn].score++;
                        num_chosen++;

                    }
            }

        }
        if(turns[counter1].value_square[1]>0)
        {
            for(a=0; a<=5; a++)
            {
                for(b=0; b<=5; b++)
                    if(a+10*b+1==(chosen[counter2++]=turns[counter1].value_square[1]))

                    {
                        Position(4*a+2,2*b+1);
                        set_color(players[turns[counter1].current_turn].color);
                        printf("X");
                        players[turns[counter1].current_turn].score++;
                        num_chosen++;

                    }
            }

        }


    }

    for(counter1=0; counter1<num_chosen; counter1++)
        fscanf(file,"%d\n",&chosen[counter1]);
    for(counter1=0; counter1<line_hor; counter1++)
        fscanf(file,"%d\n",&ch_hor[counter1]);
    for(counter1=0; counter1<line_ver; counter1++)
        fscanf(file,"%d\n",&ch_ver[counter1]);

    fscanf(file,"%d\n%d\n",&easy,&comp);

    fclose(file);

    if(easy&&comp)
        playvscomp_easy();
    else if(easy&&!comp)
        play_easy();
    else if(!easy&&comp)
        playvscomp_expert();
    else if(!easy&&!comp)
        play_expert();
}
void load3()
{

    reset_all(ch_hor,ch_ver,chosen,36);
    system("cls");
    int a,b,counter1,counter2;
    int z=turns[0].current_turn;
    file=fopen("Save3.txt","r");
    fscanf(file,"%s\n%s\n%d\n%d\n",players[0].Name,players[1].Name,&players[0].moves,&players[1].moves);

    fscanf(file,"%d\n%d\n%d\n",&t,&undone,&i);
    for (counter1=0 ; counter1<t+undone ; counter1++)
    {
        fscanf(file," %d %d %d %d %d %d\n",&turns[counter1].last_drawn,&turns[counter1].value_drawn,&turns[counter1].current_turn,&turns[counter1].passed,&turns[counter1].value_square[0],&turns[counter1].value_square[1]);
    }
    for (counter1=0 ; counter1<t ; counter1++)
    {
        if (turns[counter1].last_drawn)
        {
            for(a=0; a<=5; a++)
            {


                for(b=0; b<=5; b++)
                {
                    if(a+10*b+1==turns[counter1].value_drawn)
                    {
                        Position(4*a,2*b+1);

                    }
                }
            }
            z=turns[counter1].current_turn;
            set_color(players[z].color);
            printf("%c",-70);

            line_ver++;
        }
        else
        {
            for(a=0; a<=5; a++)
            {


                for(b=0; b<=5; b++)
                {
                    if(a+10*b+1==turns[counter1].value_drawn)
                    {
                        Position(4*a+1,2*b);

                    }
                }
            }
            z=turns[counter1].current_turn;
            set_color(players[z].color);
            printf("%c%c%c",-51,-51,-51);

            line_hor++;


        }
    }
    for(counter1=0; counter1<t; counter1++)
    {
        if(turns[counter1].value_square[0]>0)
        {
            for(a=0; a<=5; a++)
            {
                for(b=0; b<=5; b++)
                    if(a+10*b+1==turns[counter1].value_square[0])

                    {
                        Position(4*a+2,2*b+1);
                        set_color(players[turns[counter1].current_turn].color);
                        printf("X");
                        players[turns[counter1].current_turn].score++;
                        num_chosen++;

                    }
            }

        }
        if(turns[counter1].value_square[1]>0)
        {
            for(a=0; a<=5; a++)
            {
                for(b=0; b<=5; b++)
                    if(a+10*b+1==(chosen[counter2++]=turns[counter1].value_square[1]))

                    {
                        Position(4*a+2,2*b+1);
                        set_color(players[turns[counter1].current_turn].color);
                        printf("X");
                        players[turns[counter1].current_turn].score++;
                        num_chosen++;

                    }
            }

        }


    }

    for(counter1=0; counter1<num_chosen; counter1++)
        fscanf(file,"%d\n",&chosen[counter1]);
    for(counter1=0; counter1<line_hor; counter1++)
        fscanf(file,"%d\n",&ch_hor[counter1]);
    for(counter1=0; counter1<line_ver; counter1++)
        fscanf(file,"%d\n",&ch_ver[counter1]);

    fscanf(file,"%d\n%d\n",&easy,&comp);

    fclose(file);

    if(easy&&comp)
        playvscomp_easy();
    else if(easy&&!comp)
        play_easy();
    else if(!easy&&comp)
        playvscomp_expert();
    else if(!easy&&!comp)
        play_expert();

}














