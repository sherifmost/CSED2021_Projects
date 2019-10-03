#include <iostream>
#include <algorithm>
#include <string>
#include <fstream>
#include <vector>
#include <map>
#include <bits/stdc++.h>
#include <math.h>
#include <iomanip>
using namespace std;
//--------------------------------------------------------------Structures---------------------------------------------------------------------//
//creating a structure containing all the information concerning the operations(no directives):
//1-mnemonic.
//2-opcode:stored in hexadecimal notation.
//3-formats allowed: 4 means 3 and 4.
//4-operands required.3
//operations in lectures only are covered and the opcode is from the reference "Leland L Beck 3rd edition".
typedef struct
{
    string mnemonic;
    int opcode;
    int format;//format 2 indicates a register to register operation which can't take a memory as operand.
    int num_operands;
} operation;
// a structure containing all the data concerning each line after processing it.
struct parrsed
{
    string label="";
    string operation="";
    string operand="";
} data_dt;
//--------------------------------------------------------------Functions---------------------------------------------------------------------//
//a function that assigns each operation to the operation table.
void assign_operations();
//a function that prints all operations for testing.
void print_operations();
//a function that validates the operation mnemonic and assigns the current operation value.
bool get_operation(string mnemonic);
string convert_to_upper(string s);
//a function that checks whether the given label is a valid name (not a register or an opcode).
bool is_valid_label(string label);
void fill_directives();
void setfilename(string s);
void setintermediatename(string s);
//the function that processes the line.
bool parsing (string line);
bool startnum(string subline);
bool emptystr(string subline);
bool validspace(string subline);
int validhex (string operand);
string readline ();

//removes the prefix if the prefix is valid to process the word without the prefix.
string remove_prefix(string s);

bool is_prefixed(string s);
bool is_symbolic(string s);
bool is_in_symtab(string s);
bool strangechar(string s);
bool add_to_symtab(string label);
bool strangeoperand(string subline);
bool parsedoperand(string subline);
//increments the location counter if the opcode is a directive based on the directive type.
void increment_loc(string s);
void parsed_intermediate_data(string line);
int power (int a,int b);
int has_or_no_comma(string operand);
string removespaces(string s);
int convtodecimal(string s);
int get_operands_count(string operands);
//to make sure that all the operands are valid.
vector<string> get_operands_separated(string operands);
//validates the operand according to the opcode where each operation has a certain operand format.
bool validate_operand(string operand);
// a function that returns the number of bytes for the byte directive.
int num_bytes(string operand);
void print_after_pass1();
bool is_expressionorfree(string operand);
bool pos_neg_num(string operand);
string upper_char(string operand,int i);

////////////////////////////////////////////////////////////////////////////////////////////////////////////pass2 functions////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void print_header();
void print_records();
void print_end();
void pass2();
void print_after_pass2();
vector<string> split_expression(string operand);
bool valid_equ_expression(string operand);
string remove_last_spaces(string s);
string read_intermediate();
string get_subs(int start,string line);
//assign the map with each register and its corresponding value.
void assign_registers();
//get memory part of the object code
string get_objcode();
string get_memory();
string get_flags();
string get_opcode();
int get_register_value(string s);
string get_hex_string(int value);
//functions to check for the relative addressing range.
bool is_valid_BASE(int address);
bool is_valid_PC(int address);
string process_literal(string s);
string evaluate_expression_simple(string s);
bool is_expression(string s);
int get_address(string s);
bool is_absolute(string s);


//--------------------------------------------------------------Global variables---------------------------------------------------------------------//
//current index of the instruction.
int current = 1;
//current value of the location counter (address of the instruction).
int loc_cntr = 0;
int start_loc = 0;
string errnow = "";
string address_now;
string endoperand="";
ifstream in;
ifstream in_list;//read intermediate
ofstream out;
ofstream objectcode;//intermediate
ofstream objfile;//object file
bool hasline = true;
string progname="";
//the current operation to be tested.
operation current_operation;
string spaces = "            **Error: ";
string spaces2 = "            **Warning: ";
string enderr  = "**";
//indicates whether the file is successfully assembled.
bool globerr = false;
//--------------------------------------------------------------Data structures---------------------------------------------------------------------//
//a map containing all of the operations having the key as a string indicating the mnemonic
//and the value is the operation structure with an iterator to be used.
map<std::string,operation> op_tab;
map<std::string, operation>::iterator it_op;
//a map containing all the label definitions and address with an iterator to be used.
map<std::string, int> sym_tab;
map<std::string, int>::iterator it_sym;
//arrays for the register names.
string registers[9] = {"A","X","L","B","S","T","F","PC","SW"};
//array for the directives names.
string directives[11] =  {"START","END","RESW","RESB","WORD","BYTE","EQU","ORG","BASE","NOBASE","LTORG"};
// array for the valid operand prefixes
char prefix[] = {'#','@'};
/////////////////////////////////////////////////////////////////////////////////////////////////pass2 variables//////////////////////////////////////////////////////////////////////////////////////////////////////////////
int PC = 0;
int BASE = 0;
bool is_PC = false;
bool is_BASE = false;
int program_len = 0;
//////////////////////////////////////////////////
//for registers manipulation.
int registers_values[9] = {0,1,2,3,4,5,6,8,9};
map<std::string, int> registers_tab;
map<std::string, int>::iterator it_reg;
vector<string> operandliterals;
vector<string>::iterator it_lit;
vector<string> absolute_labels;
vector<string>::iterator it_absolute;
vector<string> textrecord;
vector<string>::iterator it_record;
vector<vector<string> > allrecords;
vector<vector<string> >::iterator it_allrecords;
// array for the valid operand prefixes


//--------------------------------------------------------------Implementation---------------------------------------------------------------------//
void assign_operations()
{
    //arithmetic memory.
    operation ADD;
    ADD.mnemonic = "ADD";
    ADD.opcode = 0x18;
    ADD.format = 4;
    ADD.num_operands = 1;
    op_tab["ADD"] = ADD;

    operation SUB;
    SUB.mnemonic = "SUB";
    SUB.opcode = 0x1C;
    SUB.format = 4;
    SUB.num_operands = 1;
    op_tab["SUB"] = SUB;

    operation MUL;
    MUL.mnemonic = "MUL";
    MUL.opcode = 0x20;
    MUL.format = 4;
    MUL.num_operands = 1;
    op_tab["MUL"] = MUL;

    operation DIV;
    DIV.mnemonic = "DIV";
    DIV.opcode = 0x24;
    DIV.format = 4;
    DIV.num_operands = 1;
    op_tab["DIV"] = DIV;

    //arithmetic register.
    operation ADDR;
    ADDR.mnemonic = "ADDR";
    ADDR.opcode = 0x90;
    ADDR.format = 2;
    ADDR.num_operands = 2;
    op_tab["ADDR"] = ADDR;

    operation SUBR;
    SUBR.mnemonic = "SUBR";
    SUBR.opcode = 0x94;
    SUBR.format = 2;
    SUBR.num_operands = 2;
    op_tab["SUBR"] = SUBR;

    operation MULR;
    MULR.mnemonic = "MULR";
    MULR.opcode = 0x98;
    MULR.format = 2;
    MULR.num_operands = 2;
    op_tab["MULR"] = MULR;

    operation DIVR;
    DIVR.mnemonic = "DIVR";
    DIVR.opcode = 0x9C;
    DIVR.format = 2;
    DIVR.num_operands = 2;
    op_tab["DIVR"] = DIVR;

    //load register
    operation LDA;
    LDA.mnemonic = "LDA";
    LDA.opcode = 0x00;
    LDA.format = 4;
    LDA.num_operands = 1;
    op_tab["LDA"] = LDA;

    operation LDB;
    LDB.mnemonic = "LDB";
    LDB.opcode = 0x68;
    LDB.format = 4;
    LDB.num_operands = 1;
    op_tab["LDB"] = LDB;

    operation LDF;
    LDF.mnemonic = "LDF";
    LDF.opcode = 0x70;
    LDF.format = 4;
    LDF.num_operands = 1;
    op_tab["LDF"] = LDF;

    operation LDL;
    LDL.mnemonic = "LDL";
    LDL.opcode = 0x08;
    LDL.format = 4;
    LDL.num_operands = 1;
    op_tab["LDL"] = LDL;

    operation LDS;
    LDS.mnemonic = "LDS";
    LDS.opcode = 0x6C;
    LDS.format = 4;
    LDS.num_operands = 1;
    op_tab["LDS"] = LDS;

    operation LDT;
    LDT.mnemonic = "LDT";
    LDT.opcode = 0x74;
    LDT.format = 4;
    LDT.num_operands = 1;
    op_tab["LDT"] = LDT;

    operation LDX;
    LDX.mnemonic = "LDX";
    LDX.opcode = 0x04;
    LDX.format = 4;
    LDX.num_operands = 1;
    op_tab["LDX"] = LDX;

    //store register
    operation STA;
    STA.mnemonic = "STA";
    STA.opcode = 0x0C;
    STA.format = 4;
    STA.num_operands = 1;
    op_tab["STA"] = STA;

    operation STB;
    STB.mnemonic = "STB";
    STB.opcode = 0x78;
    STB.format = 4;
    STB.num_operands = 1;
    op_tab["STB"] = STB;

    operation STF;
    STF.mnemonic = "STF";
    STF.opcode = 0x80;
    STF.format = 4;
    STF.num_operands = 1;
    op_tab["STF"] = STF;

    operation STL;
    STL.mnemonic = "STL";
    STL.opcode = 0x14;
    STL.format = 4;
    STL.num_operands = 1;
    op_tab["STL"] = STL;

    operation STS;
    STS.mnemonic = "STS";
    STS.opcode = 0x7C;
    STS.format = 4;
    STS.num_operands = 1;
    op_tab["STS"] = STS;

    operation STT;
    STT.mnemonic = "STT";
    STT.opcode = 0x84;
    STT.format = 4;
    STT.num_operands = 1;
    op_tab["STT"] = STT;

    operation STX;
    STX.mnemonic = "STX";
    STX.opcode = 0x10;
    STX.format = 4;
    STX.num_operands = 1;
    op_tab["STX"] = STX;
    //load and store character.
    operation LDCH;
    LDCH.mnemonic = "LDCH";
    LDCH.opcode = 0x50;
    LDCH.format = 4;
    LDCH.num_operands = 1;
    op_tab["LDCH"] = LDCH;

    operation STCH;
    STCH.mnemonic = "STCH";
    STCH.opcode = 0x54;
    STCH.format = 4;
    STCH.num_operands = 1;
    op_tab["STCH"] = STCH;
    //register move operation
    operation RMO;
    RMO.mnemonic = "RMO";
    RMO.opcode = 0xAC;
    RMO.format = 2;
    RMO.num_operands = 2;
    op_tab["RMO"] = RMO;
    //compare operations.
    operation COMP;
    COMP.mnemonic = "COMP";
    COMP.opcode = 0x28;
    COMP.format = 4;
    COMP.num_operands = 1;
    op_tab["COMP"] = COMP;

    operation COMPR;
    COMPR.mnemonic = "COMPR";
    COMPR.opcode = 0xA0;
    COMPR.format = 2;
    COMPR.num_operands = 2;
    op_tab["COMPR"] = COMPR;
    //jump operations.
    operation J;
    J.mnemonic = "J";
    J.opcode = 0x3C;
    J.format = 4;
    J.num_operands = 1;
    op_tab["J"] = J;

    operation JEQ;
    JEQ.mnemonic = "JEQ";
    JEQ.opcode = 0x30;
    JEQ.format = 4;
    JEQ.num_operands = 1;
    op_tab["JEQ"] = JEQ;

    operation JGT;
    JGT.mnemonic = "JGT";
    JGT.opcode = 0x34;
    JGT.format = 4;
    JGT.num_operands = 1;
    op_tab["JGT"] = JGT;

    operation JLT;
    JLT.mnemonic = "JLT";
    JLT.opcode = 0x38;
    JLT.format = 4;
    JLT.num_operands = 1;
    op_tab["JLT"] = JLT;
    //increment operations.
    operation TIX;
    TIX.mnemonic = "TIX";
    TIX.opcode = 0x2C;
    TIX.format = 4;
    TIX.num_operands = 1;
    op_tab["TIX"] = TIX;

    operation TIXR;
    TIXR.mnemonic = "TIXR";
    TIXR.opcode = 0xB8;
    TIXR.format = 2;
    TIXR.num_operands = 1;
    op_tab["TIXR"] = TIXR;
    //subroutine operations.
    operation JSUB;
    JSUB.mnemonic = "JSUB";
    JSUB.opcode = 0x48;
    JSUB.format = 4;
    JSUB.num_operands = 1;
    op_tab["JSUB"] = JSUB;

    operation RSUB;
    RSUB.mnemonic = "RSUB";
    RSUB.opcode = 0x4C;
    RSUB.format = 4;
    RSUB.num_operands = 0;
    op_tab["RSUB"] = RSUB;
    //I/O operations.
    operation TD;
    TD.mnemonic = "TD";
    TD.opcode = 0xE0;
    TD.format = 4;
    TD.num_operands = 1;
    op_tab["TD"] = TD;

    operation RD;
    RD.mnemonic = "RD";
    RD.opcode = 0xD8;
    RD.format = 4;
    RD.num_operands = 1;
    op_tab["RD"] = RD;

    operation WD;
    WD.mnemonic = "WD";
    WD.opcode = 0xDC;
    WD.format = 4;
    WD.num_operands = 1;
    op_tab["WD"] = WD;
}
void print_operations()
{
    cout<<"number of operations = "<<op_tab.size()<<endl;
    for(  it_op = op_tab.begin(); it_op != op_tab.end(); ++it_op )
    {
        operation current = it_op->second;
        cout<<"key: "<<it_op->first<<endl;
        printf("mnemonic: %s\nopcode: %X\nformat: %d\nnumber of operands: %d\n",current.mnemonic.c_str(),current.opcode,current.format,current.num_operands);
    }
}
bool get_operation(string mnemonic)
{
    bool is_format_4 = (mnemonic[0] == '+');
    if(is_format_4)
        mnemonic = mnemonic.substr(1,mnemonic.size());
    it_op = op_tab.find(mnemonic);
    if(it_op != op_tab.end())
    {
        current_operation = it_op->second;
        if (current_operation.format !=4 && is_format_4)
        {
            string s = spaces +"unsupported format 4 operation" + enderr;
            errnow = s;
            globerr = true;
            return false;
        }
        return true;
    }
    string s = spaces +"Invalid operation" + enderr;
    errnow = s;
    globerr = true;
    return false;
}
string convert_to_upper(string s)
{
    int length = s.size();
    int i;
    for(i = 0; i < length; i++)
    {
        s.at(i) = toupper(s.at(i));
    }
    return s;
}

void setfilename(string s)
{
    in.open(s.c_str());
}

void setintermediatename(string s)
{
    in_list.open(s.c_str());
}

string upper_char(string operand,int i)
{
    if(emptystr(operand))
    {
        return "";
    }
    if(operand.size()<=i)
    {
        return "";
    }
    operand[i] =  toupper(operand[i]);
    return operand;
}

bool is_in_literals(string operand)
{
    it_lit = find(operandliterals.begin(),operandliterals.end(),operand);
    return it_lit != operandliterals.end();
}

bool parsing (string line)
{
    string err = "";
    string lab = "";
    if(line.size()>0&&line.size()<9)
    {
        lab = line.substr(0,line.size());
    }
    else
    {
        lab = line.substr(0,8);
        if(line[8]!=' '&&line[8]!='\0')
        {
            err = spaces +"syntax error : limit exceeded" + enderr;
            errnow = err;
            globerr = true;
            return false;
        }
    }

    if(lab.size())

        if(startnum(lab))
        {
            err = spaces + "symbolic labels can't start with a number" + enderr;
            errnow = err;
            globerr = true;
            return false;
        }
    if(!validspace(lab)&&lab[0]==' ')
    {
        err = spaces + "misplaced label" + enderr;
        errnow = err;
        globerr = true;
        return false;
    }
    if(!validspace(lab) || strangechar(lab))
    {
        err = spaces + "invalid label " + enderr;
        errnow = err;
        globerr = true;
        return false;
    }
    string op = "";
    if(line.size()>8&&line.size()<16)
    {
        op = line.substr(9,line.size()-9);
    }
    else if (line.size()>=16)
    {
        op = line.substr(9,6);

        if((line[15]!='\0'&&line[15]!=' ') || (line[16]!='\0'&&line[16]!=' ') )
        {
            err = spaces + "syntax error : limit exceeded" + enderr;
            errnow = err;
            globerr = true;
            return false;
        }
    }

    if(emptystr(op))
    {
        err = spaces + "missing op" + enderr;
        errnow = err;
        globerr = true;
        return false;
    }
    if(!validspace(op)&&op[0]==' ')
    {
        err = spaces + "misplaced operation" + enderr;
        errnow = err;
        globerr = true;
        return false;
    }
    if((op[0]=='+'&&(!validspace(op.substr(1,op.size()-1))||strangechar(op.substr(1,op.size()-1)))) || (op[0]!='+'&& (!validspace(op)|| strangechar(op))))
    {
        err = spaces + "invalid operation or written incorrectly" + enderr;
        errnow = err;
        globerr = true;
        return false;
    }


    size_t linelen = line.find_last_not_of('\0');
    string operand = "";
    if(line.size()>17&&line.length()<36)
    {
        operand = line.substr(17,line.size()-17);
    }
    else if(line.length()>=37)
    {
        operand = line.substr(17,18);
    }
    size_t byte_size = 0;
    if(removespaces(convert_to_upper(op)) == "BYTE")
    {
        byte_size = operand.find_last_not_of(" ");
        data_dt.label = convert_to_upper(removespaces(lab));
        data_dt.operation = convert_to_upper( removespaces(op));
        data_dt.operand = upper_char(operand.substr(0,byte_size+1),0);
        return true;
    }

    if(!emptystr(operand)&&operand[0]=='='/*&&process_literal(upper_char(remove_last_spaces(operand),1))!=""*/)  // literals
    {
        data_dt.label = convert_to_upper(removespaces(lab));
        data_dt.operation = convert_to_upper( removespaces(op));
        data_dt.operand = upper_char(remove_last_spaces(operand),1);
        if(!is_in_literals(data_dt.operand))
        {
            operandliterals.push_back(data_dt.operand);
        }
        return true;
    }

    else if(!validspace(operand)&&operand[0]==' ')
    {
        err = spaces + "misplaced operand" + enderr;
        errnow = err;
        globerr = true;
        return false;
    }

    else if(remove_last_spaces(convert_to_upper(op))=="WORD"){
         byte_size = operand.find_last_not_of(" ");
        data_dt.label = convert_to_upper(remove_last_spaces(lab));
        data_dt.operation = convert_to_upper( remove_last_spaces(op));
        data_dt.operand = convert_to_upper(operand.substr(0,byte_size+1));
        return true;
    }

    else if(!validspace(operand) || !parsedoperand(operand)|| has_or_no_comma(operand)>1)
    {
        err = spaces + "invalid operand" + enderr;
        errnow = err;
        globerr = true;
        return false;
    }
    data_dt.label =convert_to_upper(removespaces(lab));
    data_dt.operation =convert_to_upper( removespaces(op));
    data_dt.operand = convert_to_upper(removespaces(operand));
    return true;
}


bool is_expressionorfree(string operand)
{
    if(operand[0]=='+'||operand[0]=='-'||operand[operand.size()-1]=='+'||operand[operand.size()-1]=='-')
    {
        return false;
    }
    int diff = count(operand.begin(),operand.end(),'-');
    int sum = count(operand.begin(),operand.end(),'+');
    if( (diff&&!sum) || (sum&&!diff) || (!sum&&!diff) )
    {
        return true;
    }
}


int has_or_no_comma(string operand)
{
    int num = 0;
    if(is_prefixed(operand))
    {
        operand = remove_prefix(operand);
    }
    if(operand[0]==','||operand[operand.size()-1]==',')
    {
        return 2;
    }
    for(int i=0; i<operand.size(); i++)
    {
        if(operand[i]==',')
        {
            num++;
            if(num>1)
                return num;
        }
    }
    return num;
}
bool startnum(string subline)
{
    if(subline[0]>='0'&&subline[0]<='9')
    {
        return true;
    }
    return false;
}
/*
check if string has only spaces
*/
bool emptystr(string subline)
{
    for(int i=0; i<subline.size(); i++)
    {
        if(subline[i]!=' ')
        {
            return false;
        }
    }
    return true;
}
/*
check for validity of spaces and characters' position
in the string
*/
bool strangechar(string subline)
{
    for(int i=0; i<subline.size(); i++)
    {
        if((subline[i]>=33&&subline[i]<=47)||(subline[i]>=58&&subline[i]<=64))
        {
            return true;
        }
    }
    return false;

}
bool parsedoperand(string subline)
{
    bool prefix = false;
    if(subline.size()==0)
    {
        return true;
    }
    if(is_prefixed(subline))
    {
        subline = remove_prefix(subline);
    }
    if(subline[0]=='*'&&subline.size()==1)
    {
        return true;
    }
    for(int i =0; i<subline.size(); i++)
    {
        /* if((subline[i]>=33&&subline[i]<=47&&subline[i]!=44)||(subline[i]>=58&&subline[i]<=63))
         {
             return false;
         }*/

        if((subline[i]>=33&&subline[i]<=47&&subline[i]<43&&subline[i]>45)||(subline[i]>=58&&subline[i]<=63))
        {
            return false;
        }
        if(!is_expressionorfree(subline))
        {
            return false;
        }
    }
    return true;

}
bool validspace(string subline)
{
    bool space = false;
    for(int i=0; i<subline.size(); i++)
    {
        if(subline[i]==' ')
        {
            space = true;
        }
        else
        {
            if(space)
            {
                return false;
            }
        }
    }
    return true;
}
string removespaces(string s)
{
    s.erase(remove(s.begin(), s.end(), ' '),s.end());
    return s;
}
bool is_register(string s)
{
    for(int i = 0; i<9; i++)
        if(registers[i] == s)
            return true;
    return false;
}
bool is_operation(string s)
{
    return !(op_tab.find(s) == op_tab.end());
}
bool isdirective(string s)
{
    for(int i=0; i<11; i++)
    {
        if(s == directives[i])
        {
            return true;
        }
    }
    return false;
}

void increment_loc(string s)
{
    if(s=="START")
    {
        string warn = spaces2 + "start again"+ enderr;
        errnow = warn;
    }
    else if ((s=="ORG" ||s=="BASE"||s=="NOBASE"||s=="LTORG" )&& data_dt.label.size() >0)
    {
        string err = spaces + "this directive can't have label"+ enderr;
        errnow = err;
        globerr = true;
    }

    else if ( (s=="NOBASE" || s=="LTORG") &&  data_dt.operand.size()>0 )
    {
        string err = spaces + "this directive can't have an operand"+ enderr;
        errnow = err;
        globerr = true;

    }
    else if (s == "BASE")
    {
        if (data_dt.operand.size() == 0)
        {
            string err = spaces + "this directive should have an operand"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(!parsedoperand(data_dt.operand))
        {
            string err = spaces + "invalid operand for this directive"+ enderr;
            errnow = err;
            globerr = true;
        }
    }
    else if (s=="END")
    {
        if(data_dt.label.size()>0)
        {
            string err = spaces + "end statement can't have a label"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(data_dt.operand.size()>0 && !is_in_symtab(remove_prefix(data_dt.operand)))
        {
            string err = spaces + "operand should be declared before end statement"+ enderr;
            errnow = err;
            globerr = true;
        }

    }

    else if(s=="EQU")
    {
        if(data_dt.label.size()==0)
        {
            string err = spaces + "this directive should have a label"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(data_dt.operand.size()==0)
        {
            string err = spaces + "this directive should have an operand"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(is_expression(data_dt.operand))
        {
            if(!valid_equ_expression(data_dt.operand))
            {

                string err = spaces + "invalid expression with equ"+ enderr;
                errnow = err;
                globerr = true;
            }
            else
            {
                sym_tab[data_dt.label] = validhex(evaluate_expression_simple(data_dt.operand));
            }

        }

        else if(data_dt.operand!="*"&&!is_in_symtab(remove_prefix(data_dt.operand)) && is_symbolic(data_dt.operand))
        {
            string err = spaces + "invalid operand with EQU statement"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(data_dt.operand!="*"&&!is_symbolic(data_dt.operand))
        {
            sym_tab[data_dt.label] = convtodecimal(data_dt.operand);
        }
        else if(is_in_symtab(remove_prefix(data_dt.operand)))
        {
            sym_tab[data_dt.label] = sym_tab[remove_prefix(data_dt.operand)];
        }

    }
    else if (s=="ORG")
    {
        if(data_dt.operand.size()==0)
        {
            string err = spaces + "this directive should have an operand"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(is_expression(data_dt.operand))
        {
            loc_cntr =  validhex(evaluate_expression_simple(data_dt.operand)) ;

        }
        else if(data_dt.operand!="*"&&!is_symbolic(remove_prefix(data_dt.operand)))
        {
            string err = spaces + "address expression is not relocatable"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(data_dt.operand!="*"&&!is_in_symtab(remove_prefix(data_dt.operand)))
        {
            string err = spaces + "invalid operand with org statement"+ enderr;
            errnow = err;
            globerr = true;
        }
        else if(is_in_symtab(remove_prefix(data_dt.operand)))
        {
            loc_cntr = sym_tab[remove_prefix(data_dt.operand)];
        }

    }
    else if (s=="RESW")
    {
        if(is_expression(data_dt.operand))
        {
            vector<string> expression = split_expression(data_dt.operand);
            if(is_symbolic(expression[0]) && is_symbolic(expression[1]))
            {
                loc_cntr += 3*validhex(evaluate_expression_simple(data_dt.operand));
            }
        }

        else if(convtodecimal(data_dt.operand)!=-1)
        {
            loc_cntr+=3*convtodecimal(data_dt.operand);
        }
    }
    else if (s=="RESB")
    {
        if(is_expression(data_dt.operand))
        {
            vector<string> expression = split_expression(data_dt.operand);
            if(is_symbolic(expression[0]) && is_symbolic(expression[1]))
            {
                loc_cntr += validhex(evaluate_expression_simple(data_dt.operand));
            }
        }
        else if(convtodecimal(data_dt.operand)!=-1)
        {
            loc_cntr+=convtodecimal(data_dt.operand);
        }
    }
    else if (s=="WORD")
    {
        if(data_dt.operand.size()==0){
            string err = spaces + "WORD must have an operand"+ enderr;
            errnow = err;
            globerr = true;
        }
        if(validspace(data_dt.operand)&&(parsedoperand(data_dt.operand)||pos_neg_num(data_dt.operand))&&has_or_no_comma(data_dt.operand)==0)
        {
            loc_cntr+=3;
        }
        else
        {
            string err = spaces + "invalid operand with WORD "+ enderr;
            errnow = err;
            globerr = true;
        }
    }
    else if (s=="BYTE")
    {
        if(num_bytes(data_dt.operand)!=-1)
        {
            loc_cntr+=num_bytes(data_dt.operand);
        }
    }
}

bool pos_neg_num(string operand){
    operand = remove_prefix(operand);
    if(convtodecimal(operand)==-1){
        return false;
    }
    return true;
}

vector<string> split_expression(string operand)
{
    vector <string> data;
    int i=0;
    string s1 = "";
    while(operand[i]!='+'&&operand[i]!='-')
    {
        s1+=operand[i];
        i++;
    }
    string s2 = operand.substr(i+1,operand.size()-s1.size()+1);
    data.push_back(s1);
    data.push_back(s2);
    string s = "";
    s+=operand[i];
    data.push_back(s);
    return data;
}

bool valid_equ_expression(string operand)
{
    vector<string> expression = split_expression(operand);
    if(is_absolute(expression[0])&&is_absolute(expression[1]))
    {
        absolute_labels.push_back(data_dt.label);
        return true;
    }
    else if(!is_absolute(expression[0])&&!is_absolute(expression[1])&&expression[2]=="-")
    {
        if(is_in_symtab(expression[0])&&is_in_symtab(expression[1]))
        {
            absolute_labels.push_back(data_dt.label);
            return true;
        }
    }
    else if(!is_absolute(expression[0])&&is_absolute(expression[1]))
    {
        if(is_in_symtab(expression[0]))
        {
            return true;
        }
    }

    return false;

}


int convtodecimal(string s)
{
    if(is_prefixed(s))
    {
        s = remove_prefix(s);
    }

    char op = '+';
    int num = 0;
    if(s[0]=='+'||s[0]=='-'){
        if(s[0]=='-'){
            op = '-';
        }
        s = s.substr(1,s.size()-1);
    }
    for(int i=s.size()-1; i>=0; i--)
    {
        if(s[i]>='0'&&s[i]<='9')
        {
            num += (s[i]-'0') * power(10,s.size()-1-i);
        }
        else
        {
            string err = spaces + "invalid characters in operand"+ enderr;
            errnow = err;
            globerr = true;
            return -1;
        }
    }

    if(op=='-'){
        return 0xFFFFFF - num  + 1;
    }

    return num;
}
bool is_indexed(string s)
{
    if(s.size()<3)
    {
        return false;
    }
    return s.substr(s.size()-2,2) == ",X";
}
bool is_prefixed(string s)
{
    return (s[0] == '@'||s[0] == '#');
}
int power (int a,int b)
{
    int ans = 1;
    for(int i=0; i<b; i++)
    {
        ans*=a;
    }
    return ans;
}

bool is_symbolic(string s)
{
    for(int i = 0; i < s.size(); i++)
    {
        if(s[i]<='Z' && s[i]>='A')
            return true;
    }
    return false;
}
string remove_prefix(string s)
{
    if(is_prefixed(s))
        return (s.substr(1,s.size()-1));
    return s;
}
bool add_to_symtab(string label)
{
    if(is_in_symtab(label))
    {
        string s = spaces + "label is declared more than once" + enderr;
        errnow = s;
        globerr = true;
        return false;
    }
    if(is_register(label))
    {
        string s = spaces + "label can't have the name of a register" + enderr;
        errnow = s;
        globerr = true;
        return false;
    }
    if(is_operation(label))
    {
        string s = spaces + "label can't have the name of an operation" + enderr;
        errnow = s;
        globerr = true;
        return false;
    }
    if(label.size()==0)
    {
        return true;
    }

    sym_tab[label] = loc_cntr;
    return true;
}
bool is_in_symtab(string label)
{
    return !(sym_tab.find(label) == sym_tab.end());
}
bool validate_operand(string operand)
{

    if(is_symbolic(operand) && startnum(operand))
    {
        string s = spaces +"symbolic operand can't start with a number" + enderr;
        errnow = s;
        globerr = true;
        return false;
    }
    if(is_indexed(operand) && is_prefixed(operand))
    {
        string s = spaces +"can't use indexed addressing with immediate or indirect addressing" + enderr;
        errnow = s;
        globerr = true;
        return false;
    }

    int format = current_operation.format;
    int allowed_num_operands = current_operation.num_operands;
    bool is_declared = is_in_symtab(operand);
    if(format == 4)                                    //pure operand
    {
        if(is_indexed(operand))
        {
            operand = operand.substr(0,operand.size()-2);
        }
        if(is_prefixed(operand))
        {
            operand = operand.substr(1,operand.size()-1);
        }
    }
    if(format == 2)
    {
        int num_operands = get_operands_count(operand);
        vector<string> operands = get_operands_separated(operand);
        if(num_operands != allowed_num_operands)
        {
            string s = spaces +"Invalid number of operands" + enderr;
            errnow = s;
            globerr = true;
            return false;
        }
        for(int i = 0; i < operands.size(); i++)
        {
            if(!is_register(operands[i]))
            {
                string s = spaces +"invalid operand: all operands must be registers" + enderr;
                errnow = s;
                globerr = true;
                return false;
            }
        }
    }
    if(format == 4)
    {
        int num_operands = get_operands_count(operand);   //  not declared in this scope
        if(is_indexed(operand))
        {
            operand = operand.substr(0,operand.size()-2);
            num_operands--;                               //  num_operands still 2
        }
        if(num_operands != allowed_num_operands)
        {
            string s = spaces +"Invalid number of operands" + enderr;
            errnow = s;
            globerr = true;
            return false;
        }
        if(is_register(operand))
        {
            string s = spaces +"invalid operand: operand must be a label or a memory address" + enderr;
            errnow = s;
            globerr = true;
            return false;
        }
    }
    return true;
}
int get_operands_count(string operands)
{
    if(operands.size()==0)
        return 0;
    int number = 1;
    for(int i = 0; i < operands.size(); i++)
    {
        if(operands[i] == ',')
            number++;
    }
    return number;
}
vector<string> get_operands_separated(string operands)
{
    string s = "";
    vector<string> returned;
    for(int i = 0; i<operands.size(); i++)
    {
        if(operands[i] == ',')
        {
            returned.push_back(s);
            s = "";
        }
        else
        {
            s+=operands[i];
        }
    }
    returned.push_back(s);
    return returned;
}
//read on line from file and save it in vector all lines and
//call parse function
string readline ()
{
    hasline = false;
    string line="";

    if(getline(in,line))
    {
        hasline = true;
    }
    return line;
}

int num_bytes(string operand)
{
    char start = operand[0];
    int number = 0;

    if((operand[0]!='X'&& operand[0]!='C')||(operand[1]!='\'')||operand[operand.size()-1]!='\'')
    {
        //print error(invalid operand format)
        string err = spaces + "invalid operand format" + enderr;
        errnow = err;
        globerr = true;
        return -1;
    }
    if(operand.size()>18)
    {
        string err = spaces + "invalid operand format" + enderr;
        errnow = err;
        globerr = true;
        return -1;
    }
    if(start == 'X')
    {
        for(int i = 2; i<operand.size()-1; i++)
        {
            if((operand[i]>='0'&&operand[i]<='9')||(operand[i]>='A'&& operand[i]<='F'))
            {
                number++;
            }
            else
            {
                string err = spaces + "not valid hexa string" + enderr;
                errnow = err;
                globerr = true;
                return -1;
            }
        }
        if(number %2)
        {
            //error odd number of hexa characters
            string err = spaces + "error odd number of hexa chars" + enderr;
            errnow = err;
            globerr = true;
            return -1;
        }
        return number/2;

    }
    return operand.size()-3;
}
int validhex (string operand)
{
    int number = 0;
    int maximum = 0XFFFF;
    if(operand.size()>4)
    {
        string err = spaces + "address operand is out of limit"+enderr;
        errnow = err;
        globerr = true;
        return 0;
    }

    for(int i=operand.size()-1; i>=0; i--)
    {

        if(operand[i]>='A'&&operand[i]<='F')
        {
            number += ((operand[i] - 'A')+10)* power(16,operand.size()-1-i);
        }
        else if(operand[i]>='0'&&operand[i]<='9')
        {
            number += (operand[i] - '0') *power(16,operand.size()-1-i);
        }
        else
        {

            string err = spaces + "invalid hexa string"+enderr;
            errnow = err;
            globerr = true;
            return 0;
        }
    }

    return number;
}

int validhexlit (string operand){
    int number = 0;
    int maximum = 0XFFFF;
    if(operand.size()>6)
    {
        string err = spaces + "address operand is out of limit"+enderr;
        errnow = err;
        globerr = true;
        return -1;
    }

    for(int i=operand.size()-1; i>=0; i--)
    {

        if(operand[i]>='A'&&operand[i]<='F')
        {
            number += ((operand[i] - 'A')+10)* power(16,operand.size()-1-i);
        }
        else if(operand[i]>='0'&&operand[i]<='9')
        {
            number += (operand[i] - '0') *power(16,operand.size()-1-i);
        }
        else
        {

            string err = spaces + "invalid hexa string"+enderr;
            errnow = err;
            globerr = true;
            return -1;
        }
    }

    return number;
}


void pass1()
{
    bool foundEnd = false;
    bool hasstart = false;
    int no_end = 0;
    assign_registers();
    assign_operations();
    string s = readline();
    out.open("intermediate.txt");
    objectcode.open("listfile.txt");
    out << "line number   "  <<"address\t" <<"label\t"<<" op\t" <<" operand"<<endl;
    objectcode << "line number   "  <<"address\t" <<"label\t"<<" op\t" <<" operand"<<endl;
    while(emptystr(s)||s[0]=='.')
    {
        if(emptystr(s))
        {
            s=readline();
            continue;
        }
        out << setw(2) <<dec <<  current<<"   ";
        out << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr<<"      ";
        out << s << endl;
        objectcode << setw(2) <<dec <<  current<<"   ";
        objectcode << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr<<"      ";
        objectcode << s << endl;
        s = readline();
        current++;
    }
    int first_address = 0;
    if(parsing(s))
    {
        if(add_to_symtab(data_dt.label))
        {
            if(isdirective(data_dt.operation))
            {
                if(data_dt.operation=="START")
                {

                    hasstart = true;
                    if(validate_operand(data_dt.operand))
                    {
                        loc_cntr = validhex(data_dt.operand);
                        start_loc = loc_cntr;
                        progname = data_dt.label;
                    }
                }
            }

        }
    }
    if(hasstart)
    {
        out << setw(2) <<dec <<  current<<"   ";
        out << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr<<"      ";
        out << s <<endl;

        objectcode << setw(2) <<dec <<  current<<"   ";
        objectcode << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr<<"      ";
        objectcode << s <<endl;
        sym_tab.clear();
        if(errnow!="")
        {
            out << errnow <<endl;
        }
        s=readline();
        while(emptystr(s)&&hasline)
        {
            s=readline();
        }
    }
    else
    {
        sym_tab.clear();
        current--;
    }
    errnow = "";
    while(hasline)
    {
        current++;
        out  <<setw(2) << dec << current<<"   ";
        out << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr<<"      ";
        out << s <<endl;
        objectcode  <<setw(2) << dec << current<<"   ";
        objectcode << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr<<"      ";
        objectcode << s <<endl;

        if(s[0] != '.')
        {
            if(parsing(s))
            {
                if(add_to_symtab(data_dt.label))
                {

                    if(isdirective(data_dt.operation))
                    {
                        increment_loc(data_dt.operation);
                        if(data_dt.operation=="END")
                        {

                            no_end++;
                            foundEnd = true;
                        }
                        if(data_dt.operation=="LTORG")
                        {
                            while(operandliterals.size()!=0)
                            {
                                sym_tab[operandliterals.front()] = loc_cntr;
                                current++;
                                out << setw(2) <<dec <<  current<<"   ";
                                out << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr <<"      ";
                                out << "*        " << operandliterals.front() << endl;
                                objectcode << setw(2) <<dec <<  current<<"   ";
                                objectcode << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr <<"      ";
                                objectcode << "*        " << operandliterals.front() << endl;
                                operandliterals.erase(operandliterals.begin());
                                loc_cntr+=3;
                            }
                        }
                    }
                    else if(get_operation(data_dt.operation))
                    {

                        if(validate_operand(data_dt.operand))
                        {
                            if(data_dt.operation[0]=='+')
                                loc_cntr+=4;
                            else if (current_operation.format==4)
                                loc_cntr+=3;
                            else
                                loc_cntr+=2;
                        }
                    }
                }
            }
        }
        if(errnow!="")
        {
            out << errnow <<endl;
            objectcode  << errnow <<endl;
        }

        s = readline();
        while(emptystr(s)&&hasline)
        {
            s=readline();

        }
        errnow = "";
    }
    if(errnow!="")
    {
        out << errnow <<endl;
        objectcode  << errnow <<endl;
    }
    if(!no_end|| no_end>1)
    {
        string err = spaces + "MISSING END STATEMENT OR REPEATED END" + enderr;
        out << err <<endl;
        objectcode  << err <<endl;
        //error
    }
    print_after_pass1();

}
void print_after_pass1()
{
    if(globerr)
    {
        out << "INCOMPLETE ASSAMBLY"<<endl;
        objectcode<< "INCOMPLETE ASSAMBLY"<<endl;
    }
    else
    {
        while(operandliterals.size()!=0)
        {
            sym_tab[operandliterals.front()] = loc_cntr;
            current++;
            out << setw(2) <<dec <<  current<<"   ";
            out << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr <<"      ";
            out << "*        " << operandliterals.front() << endl;
            objectcode << setw(2) <<dec <<  current<<"   ";
            objectcode << "         " << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex<< loc_cntr <<"      ";
            objectcode << "*        " << operandliterals.front() << endl;
            operandliterals.erase(operandliterals.begin());
            loc_cntr+=3;
        }
        out << "PASS 1 COMPLETED SUCCESSFULLY"<<endl;
        out << "LABEL"  << "              VALUE"<<endl;
        objectcode << "PASS 1 COMPLETED SUCCESSFULLY"<<endl;
        objectcode << "LABEL"  << "              VALUE"<<endl;
        for(it_sym=sym_tab.begin(); it_sym!=sym_tab.end(); it_sym++)
        {

            if(it_sym->first[0] != '=')
            {
                out << left << setfill('\0')<< setw(8) <<   (it_sym->first) <<"           "<< right << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex <<
                    (it_sym->second) << endl;

                    objectcode << left << setfill('\0')<< setw(8) <<   (it_sym->first) <<"           "<< right << setfill('0') << setw(4)<<setiosflags(ios_base::uppercase)<< hex <<
                    (it_sym->second) << endl;
            }
        }

    }
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////pass 2 functions ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

string get_subs(int start,string line)
{
    string s ="";
    if(start == 33)   //operation
    {
        while(line[start]!='\0'&&start<39)
        {
            s = s+line[start++];
        }
    }
    else  // operand   start = 41
    {
        while(line[start]!='\0'&&start<59)
        {
            s = s+line[start++];
        }
    }
    return s;
}

int new_pc()
{
    string op = data_dt.operation;
    string operand = data_dt.operand;
    string lab = data_dt.label;
    if(lab=="*")
    {
        PC+=3;
    }
    else if(isdirective(op))
    {
        if(op=="BYTE")
        {
            PC += num_bytes(operand);
        }
        else if(op=="WORD")
        {
            PC+=3;
        }

    }
    else
    {
        if(op[0]=='+')
        {
            PC +=4;
        }
        else if(current_operation.format==4)
        {
            PC+=3;
        }
        else if(current_operation.format==2)
        {
            PC+=2;
        }
    }
    return PC;

}

void parsed_intermediate_data(string line)
{
    data_dt.label = "";
    data_dt.operation="";
    data_dt.operand="";
    PC = 0;
    string current_address = line.substr(14,4);
    address_now = current_address;
    PC = validhex(current_address);
    loc_cntr = PC;
    if(line[24]!='.')
    {
        string current_label = line.substr(24,8);
        current_label = remove_last_spaces(current_label);
        data_dt.label = convert_to_upper(current_label);

        if(data_dt.label!="*")
        {

            string current_op = get_subs(33,line);
            current_op = convert_to_upper(remove_last_spaces(current_op));
            data_dt.operation = convert_to_upper(current_op);

            string current_operand = get_subs(41,line);
            current_operand = remove_last_spaces(current_operand);



            if(!isdirective(data_dt.operation))
            {
                get_operation(data_dt.operation);
                if(current_operand[0]=='=')
                {
                    data_dt.operand = upper_char(current_operand,1);
                }
                else
                {
                    data_dt.operand =convert_to_upper(current_operand);
                }
            }
            else
            {
                if(data_dt.operation=="BYTE")
                {
                    data_dt.operand = upper_char(current_operand,0);
                }
                else
                {
                    data_dt.operand =convert_to_upper(current_operand);
                }
                if(data_dt.operation=="BASE")
                {
                    if(is_expression(data_dt.operand))
                    {
                        BASE = validhex(evaluate_expression_simple(data_dt.operand));
                    }
                    if(!is_symbolic(data_dt.operand))
                    {
                        string tempoperand = remove_prefix(data_dt.operand);
                        BASE = convtodecimal(tempoperand);
                    }
                    else
                    {
                        string tempoperand = remove_prefix(data_dt.operand);
                        if(is_in_symtab(tempoperand))
                        {
                            BASE = sym_tab[tempoperand];
                        }
                        else
                        {
                            //error
                        }
                    }
                }

            }

        }
        else
        {
            size_t literal_size = line.find_last_not_of('\0');
            data_dt.operation = remove_last_spaces(line.substr(33,literal_size-32));
        }

        PC = new_pc();

    }

}

void assign_registers()
{
    for(int i = 0; i < 9; i ++)
        registers_tab[registers[i]] = registers_values[i];
}
int get_register_value(string s)
{
    it_reg = registers_tab.find(s);
    return it_reg->second;
}
string get_hex_string(int value)
{
    std::stringstream stream;
    stream << std::hex << value;
    std::string result( stream.str() );
    return convert_to_upper(result);
}
string get_objcode()
{
    //first get the operand and the operation and process them.
    string operation = data_dt.operation;
    string operand = data_dt.operand;
    //reset the relative addressing booleans.
    is_PC = false;
    is_BASE = false;
    if(operation == "WORD")
    {
        if(is_prefixed(operand))
            operand = remove_prefix(operand);
        if(is_indexed(operand))
            operand = operand.substr(0,operand.size()-2);


        string returned;

        if(is_expression(operand))
        {
            vector<string> operands = split_expression(operand);
            if((is_symbolic(operands[0]) && !is_in_symtab(operands[0])) || (is_symbolic(operands[1]) && !is_in_symtab(operands[1])))
            {
                //error operand was not declared////////////////////////////////
                globerr = true;
                string err = spaces + "error operand was not declared" + enderr;
                errnow = err;
                return "";
            }
            returned = evaluate_expression_simple(operand);
        }
        else if(is_symbolic(operand))
        {
            if(!is_in_symtab(operand))
            {
                //error operand wasn't declared/////////////////
                globerr = true;
                string err = spaces + "error operand was not declared" + enderr;
                 errnow = err;
                return "";
            }
            returned = sym_tab[operand];
        }
        else
            returned = get_hex_string(convtodecimal(operand));
        if(returned.size() > 6)
        {
            //error length of word is out of bounds//////////////////////////////////
            globerr = true;
            string err = spaces + "error length of word is out of bounds" + enderr;
            errnow = err;
            return "";
        }
        while(returned.size()!=6)
            returned = "0" + returned;
        return returned;
    }
    else if(operation == "BYTE")
    {
        string returned = "";
        if(operand[0] == 'C')
        {
            for(int i = 2; i < operand.size()-1; i++)
                returned += get_hex_string((int)operand[i]);
            if(returned.size()%2)
                returned = "0" + returned;
        }
        else
            returned = operand.substr(2,operand.size()-3);

        return returned;
    }
    else if(operation[0] == '=') // a literal value.
    {
        return process_literal(operation);
    }
    else
    {
        if(operation[0] == '+')
            operation = operation.substr(1,operation.size()-1);
        if(!is_operation(operation))
            return "";// a directive so it has no object code.
        get_operation(operation);
        string opCode = get_opcode();
        string memCode = get_memory();
        string flagCode = get_flags();
        if(memCode.size() == 0)
                return "";///////////////////////////////error occurred in memory calculation.

        return opCode+flagCode+memCode;
    }

}
/////////////////////////////////////////
string get_memory()// returns an empty string if an error occurred.
{
    string operand = data_dt.operand;
    int total_size;
    int address;
    string returned = "";
    bool direct = (data_dt.operation[0] == '+');
    if(current_operation.format == 2)
    {
        vector<string> operands = get_operands_separated(operand);
        for(int i = 0; i < operands.size(); i++)
            returned += get_hex_string(get_register_value(operands[i]));
        if(returned.size() == 1)
            returned += "0";
        is_PC = false;
        is_BASE = false;
        return returned;
    }
    if(operand.size() == 0) //RSUB need to look how to handle it//////////////////////////////////////////////////////////////////////
    {
        is_PC = false;
        is_BASE = false;
        if(data_dt.operation[0] == '+')
            return "00000";
        return "000";
    }
    if(is_indexed(operand))//remove the indexing part.
        operand = operand.substr(0,operand.size()-2);
    if(is_prefixed(operand))
        operand = remove_prefix(operand);
    if(direct)
        total_size = 5;
    else
        total_size = 3;

    if(is_expression(operand)&&operand[0]!='=')// is a simple expression that should be evaluated.
    {
        is_PC = false;
        is_BASE = false;
        returned = evaluate_expression_simple(operand);
        if(returned.size() == 0)//an error occurred.
            return "";
    }
    else// a single operand.
    {
        if(is_symbolic(operand) && !is_in_symtab(operand))
        {
            //error operand was not declared////////////////////////////////
            globerr = true;
            string err = spaces + "error operand was not declared" + enderr;
            errnow = err;
            return "";
        }

        if(operand == "*")
            address = loc_cntr;
        else if(is_symbolic(operand))
            address = sym_tab.find(operand)->second;
        else{
            address = convtodecimal(operand);
        }
        if((data_dt.operand[0] == '#' && !is_symbolic(operand)) || direct ) //immediate addressing or format 4 direct addressing or a literal is the operand.
        {
            returned = get_hex_string(address);
            is_PC = false;
            is_BASE = false;

        }
        else if(is_valid_PC(address))   //not immediate addressing nor format 4 so may be PC relative or BASE relative,
        {
            //we try PC first then BASE and if both are out of range an error is set.
            //the PC and BASE relative displacements are calculated and 2's complement is to
            //be used in case a negative value is resulted.
            is_PC = true;
            is_BASE = false;
            returned = get_hex_string(address - PC);
            if(returned.size() > 3)//for the 2's complement representations that lead to several bits.
                returned = returned.substr(returned.size()-3,3);
        }
        else if(is_valid_BASE(address))
        {
            is_PC = false;
            is_BASE = true;
            returned = get_hex_string(address - BASE);
            if(returned.size() > 3)//for the 2's complement representations that lead to several bits.
                returned = returned.substr(returned.size()-3,3);
        }
        else  // failed both relative PC and BASE addressing so error.
        {
            globerr = true;
            string err = spaces + "error address is out of range of both PC and BASE relative addressing" + enderr;
            errnow = err;
            //error address is out of range of both PC and BASE relative addressing///////////////////////////////////////////////////
            return "";
        }
    }
    if(returned.size() > total_size)
    {
            globerr = true;
            string err = spaces + "error the address is out of range" + enderr;
            errnow = err;
        //error the address is out of range/////////////////////////////////////
        return "";
    }
    while(returned.size()!=total_size)//add zeros to have the correct number of hex bits.
        returned = "0" + returned;
    return returned;
}
bool is_valid_PC(int address)
{
    if(address - PC > 2047 || address - PC < -2048)
        return false;
    return true;
}
bool is_valid_BASE(int address)
{
    if(address - BASE > 4095 || address - BASE < 0)
        return false;
    return true;
}
string get_flags()
{
    int value = 0;
    if(current_operation.format == 2)//no flags for registers.
        return "";
    if(data_dt.operation[0] == '+')//format 4;
        value += 1;
    if(is_PC)
        value += 2;
    if(is_BASE)
        value += 4;
    if(is_indexed(data_dt.operand))
        value += 8;
    return get_hex_string(value);
}
string get_opcode()
{
    int value = current_operation.opcode;
    if(current_operation.format == 2)//registers have no flags
        return get_hex_string(value);
    if(!is_prefixed(data_dt.operand))
        value += 3;
    else if(data_dt.operand[0] == '#')
        value += 1;
    else
        value += 2;
    string returned = get_hex_string(value);
    if(returned.size() == 1)
        returned = "0" + returned;
    return returned;
}

string process_literal(string s)
{
    if(s[1] == '*')
        return get_hex_string(loc_cntr);
    if(s[1] != 'W' && s[1] != 'C' && s[1] != 'X' || s[2] != '\'' || s[s.size()-1] != '\'' || s.size() <= 4)
    {
        globerr = true;
            string err = spaces + "error invalid literal format" + enderr;
            errnow = err;
        //error invalid literal format///////////////////////////////////////////////////////////
        return "";
    }
    string returned = "";
    if(s[1] == 'W')
    {
        if(is_symbolic(convert_to_upper(s.substr(3,s.size()-4)))){
            globerr = true;
            string err = spaces + "not decimal literal" + enderr;
            errnow = err;
            return "";
        }
        returned = get_hex_string(convtodecimal(s.substr(3,s.size()-4)));
    }
    else if(s[1] == 'C')
    {
        for(int i = 3; i < s.size()-1; i++)
            returned += get_hex_string((int)s[i]);
        if(returned.size()%2)
            returned = "0" + returned;
    }
    else
    {
        if(validhexlit(s.substr(3,s.size()-4)) == -1)
        {
            globerr = true;
            string err = spaces + "not a valid hexadecimal literal" + enderr;
            errnow = err;
            return "";
        }
        returned = s.substr(3,s.size()-4);
        if(returned.size()%2)
        {
            globerr = true;
            string err = spaces + "error odd length of hex string" + enderr;
            errnow = err;
            //error odd length of hex string /////////////////////////////////////////////////////////////////
            return "";
        }
    }
    if(returned.size()>6)
    {
        globerr = true;
            string err = spaces + "error literal length out of bounds" + enderr;
            errnow = err;
           // objectcode  << err <<endl;

        //error literal length out of bounds/////////////////////////////////////////////////
        return "";
    }
    while(returned.size()<6)
        returned = "0" + returned;
    return returned;
}

bool is_expression(string s) // checks for a simple expression and must have a "+" or a "-".
{
    for(int i = 1 ; i < s.size(); i++)
        if(s[i] == '+' || s[i] == '-')
            return true;
    return false;
}
string evaluate_expression_simple(string s)
{
    int opLoc = 0;
    char op;
    int result;
    for(int i = 0; i < s.size(); i++)
    {
        if(s[i] == '+' || s[i] == '-')
        {
            opLoc = i;
            break;
        }
    }
    string op1 = s.substr(0,opLoc);
    string op2 = s.substr(opLoc+1,s.size()-op1.size()-1);
    op = s[opLoc];
    if(op == '-')
    {
        if(is_absolute(op1) && !is_absolute(op2))
        {
            globerr = true;
            string err = spaces + "error cannot have a negative relocatable address in the expression" + enderr;
            errnow = err;
            //error cannot have a negative relocatable address in the expression////////////////////////////
            return "";
        }
        result = get_address(op1) - get_address(op2);
        if(result < 0)
        {
            globerr = true;
            string err = spaces + "error negative address value is invalid" + enderr;
            errnow = err;
            //error negative address value is invalid/////////////////////////
            return "";
        }
        return get_hex_string(result);
    }
    else if(!is_absolute(op1) && !is_absolute(op2))
    {
            globerr = true;
            string err = spaces + "error cannot have an addition between two relocatable addresses" + enderr;
            errnow = err;
            //error cannot have an addition between two relocatable addresses/////////////////////////
            return "";
    }
    return get_hex_string(get_address(op1) + get_address(op2));
}
int get_address(string s)
{
    if(is_symbolic(s))
        return sym_tab.find(s)->second;
    return convtodecimal(s);
}
bool is_absolute(string s)
{
    if(! is_symbolic(s))
        return true;
    std::vector<string>::iterator it = std::find(absolute_labels.begin(), absolute_labels.end(), s);
    return it != absolute_labels.end();
}

string remove_last_spaces(string s)
{
    if(emptystr(s))
    {
        return "";
    }
    int i=s.size()-1;
    while(s[i]==' ')
    {
        i--;
    }
    return s.substr(0,i+1);
}

string read_intermediate()
{

    string s = "";
    getline(in_list,s);
    return s;
}

int get_bytes_text(vector<string> text)
{
    int sizenow = 0;
    for(it_record = text.begin() ; it_record!=text.end(); it_record++)
    {
        if(it_record!=text.begin())
        {
            string s = *it_record;
            sizenow+=s.size();
        }
    }
    return sizenow;
}

void new_record(string current_ad,int current_size)
{
    if(textrecord.size()==0){
        return;
    }
    vector<string>::iterator it = textrecord.begin();
    advance(it,1);
    textrecord.insert(it,get_hex_string(current_size/2));
    allrecords.push_back(textrecord);
    textrecord.clear();
    textrecord.push_back(current_ad);
}

void fillrecords(string obcode)
{
    string op = data_dt.operation;
    string current_ad = address_now;
    string obj_code = obcode;
    int allowed_size = 60;
    int current_size = get_bytes_text(textrecord);
    if(op=="ORG"||op=="RESW"||op=="RESB")
    {
        new_record(current_ad,current_size);
        textrecord.clear();
    }
    if(obj_code!="") //textrecord[1] = address
    {


        if(textrecord.size()==0)
        {
            textrecord.push_back(current_ad);
        }
        if(current_size+obj_code.size() >allowed_size)   // new record
        {
            new_record(current_ad,current_size);
        }
        textrecord.push_back(obj_code);


    }
}

/*int main(int argc, char *argv[])
{
    string input_file = "code1.txt";
    if(argc>=2){
        input_file= argv[1];
    }
    setfilename(input_file);
    pass1();
    return 0;
}*/

void pass2(){
    data_dt.label = "";
    data_dt.operation="";
    data_dt.operand="";
    string filename = "intermediate.txt";
    setintermediatename(filename);
    string s = read_intermediate();
    objectcode << s  + "                    OBJECT CODE"<< endl;
    int start = start_loc;
    int prog_len = 0;
   // bool has_org = false;
    while(s!=""&&remove_last_spaces(s)!="PASS 1 COMPLETED SUCCESSFULLY")
    {
        errnow = "";
        s = read_intermediate();
        if(remove_last_spaces(s)!="PASS 1 COMPLETED SUCCESSFULLY"){
        while(s.size()<70)
        {
            s+=" ";
        }
        parsed_intermediate_data(s);
        objectcode << s;
        string objcode = get_objcode();
        objectcode << objcode <<endl;
        if(errnow!=""){
            objectcode << errnow << endl;
        }
        fillrecords(objcode);

        if(data_dt.operation=="ORG"||data_dt.operation=="END")
        {
            if(data_dt.operation=="END"){
                endoperand = data_dt.operand;
            }
            prog_len+=(loc_cntr-start);
            if(data_dt.operation=="ORG"){
            start = sym_tab[remove_prefix(data_dt.operand)];
            }
        }
    }
    else{
       int current_size = get_bytes_text(textrecord);
        new_record(address_now,current_size);
        textrecord.clear();
    }
    }

    program_len = prog_len+1;
    print_after_pass2();
}

void print_after_pass2(){
    if(globerr){
        objectcode << "INCOMPLETE ASSEMBLY"<<endl;
    }
    else{

        objectcode << "PASS 2 COMPLETED SUCCESSFULLY"<<endl;
        objectcode << "COMPLETE ASSEMBLY"<<endl;
        objfile.open("objectfile.txt");
        print_header();
        print_records();
        print_end();
    }

}

void print_records(){
    for(int i=0;i<allrecords.size();i++){
            objfile << "T" <<"^";
        for(int j=0;j<allrecords[i].size();j++){
            if(j==allrecords[i].size()-1){
                 objfile<< allrecords[i][j];
            }else{
                if(j==0){
                    objfile<<setfill('0') << setw(6);
                }
                if(j==1){
                    objfile<<setfill('0') << setw(2);
                }
                 objfile<< allrecords[i][j]<<"^";
            }

        }
        objfile <<endl;
    }
}

void print_header(){
    objfile << "H" <<"^"<< left << setfill(' ') << setw(6)<<progname<<"^"<<right<<setfill('0') << setw(6)<<
    setiosflags(ios_base::uppercase) << hex << start_loc<<"^" << setfill('0') << setw(6)<<setiosflags(ios_base::uppercase) <<hex <<program_len<<endl;
}

void print_end(){
    if(endoperand == ""){
       objfile << "E"<< "^"<< setfill('0') << setw(6)<<setiosflags(ios_base::uppercase) <<hex<<start_loc<<endl;
    }
    else{
        objfile << "E"<< "^"<<setfill('0') << setw(6)<< setiosflags(ios_base::uppercase) <<hex<<sym_tab[remove_prefix(endoperand)]<<endl;
    }

}

void assembly(){
    pass1();
    if(!globerr){
        pass2();
    }
}

int main()
{

    setfilename("code.txt");
    assembly();
    return 0;
}
