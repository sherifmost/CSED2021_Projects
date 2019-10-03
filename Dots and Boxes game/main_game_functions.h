#ifndef MAIN_GAME_FUNCTIONS_H_INCLUDED
#define MAIN_GAME_FUNCTIONS_H_INCLUDED
#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <dos.h>
#include <dir.h>
#include <conio.h>
#include <time.h>
#define name_len 80
typedef struct //structure containing data for each turn.
{
    int last_drawn;
    int value_drawn;
    int current_turn;
    int value_square[2];
    int passed;

} turn;



typedef struct //structure containing data for each player.
{
    int color;
    int score;
    int moves;
    char Name[name_len+1];
} player_data;



typedef struct//structure containing data for the leader boards.
{
    char name[name_len+1];
    int score;
}leader_board;

//this file contains the functions that are used through the whole game.

//basic functions
void Position(int X, int Y);
void set_color(int ForgC);
void display_current();
void reset_all(int r[],int a[],int b[],int t);
void Timer(time_t start);
void undo();
void redo();
//expert mode functions.
void play_expert();
void players_expert();
int check_expert_hor(int x,int y,int A[]);
int check_expert_ver(int x,int y,int A[]);
void drawshape_expert();
void Point_expert(int ch_hor[],int ch_ver[] );
void display_results_expert ();
void playvscomp_expert();
void playervscomp_expert();
void comp_expert_random();

//easy mode functions.
void play_easy();
void players_easy();
int check_easy_hor(int x,int y,int A[]);
int check_easy_ver(int x,int y,int A[]);
void drawshape_easy();
void Point_easy(int ch_hor[],int ch_ver[] );
void display_results_easy ();
void comp_easy_random();
void playervscomp_easy();
void playvscomp_easy();

//full game function (main function).
void full_game();

//save/load and high scores functions.
int lines_in_file();
void high_scores_easy_pvsp(char rank_name[],int rank_score);
void high_scores_easy_pvsc(char rank_name[],int rank_score);
void high_scores_expert_pvsp(char rank_name[],int rank_score);
void high_scores_expert_pvsc(char rank_name[],int rank_score);
void print_top_10();
void save1();
void save2();
void save3();
void load1();
void load2();
void load3();

#endif // MAIN_GAME_FUNCTIONS_H_INCLUDED
