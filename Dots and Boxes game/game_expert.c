#include <stdio.h>
#include <stdlib.h>
#include <windows.h>
#include <dos.h>
#include <dir.h>
#include <conio.h>
#define name_len 80
#include"main_game_functions.h"

//this file contains functions for expert mode.
void play_expert();
void players_expert();
int check_expert_hor(int x,int y,int A[]);
int check_expert_ver(int x,int y,int A[]);
void drawshape_expert();
void Point_expert(int ch_hor[],int ch_ver[] );
void display_results_expert ();

