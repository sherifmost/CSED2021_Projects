function varargout = rootgui(varargin)
% ROOTGUI MATLAB code for rootgui.fig
%      ROOTGUI, by itself, creates a new ROOTGUI or raises the existing
%      singleton*.
%
%      H = ROOTGUI returns the handle to a new ROOTGUI or the handle to
%      the existing singleton*.
%
%      ROOTGUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in ROOTGUI.M with the given input arguments.
%
%      ROOTGUI('Property','Value',...) creates a new ROOTGUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before rootgui_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to rootgui_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help rootgui

% Last Modified by GUIDE v2.5 14-May-2019 13:10:54

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @rootgui_OpeningFcn, ...
                   'gui_OutputFcn',  @rootgui_OutputFcn, ...
                   'gui_LayoutFcn',  [] , ...
                   'gui_Callback',   []);
if nargin && ischar(varargin{1})
    gui_State.gui_Callback = str2func(varargin{1});
end

if nargout
    [varargout{1:nargout}] = gui_mainfcn(gui_State, varargin{:});
else
    gui_mainfcn(gui_State, varargin{:});
end
end
% End initialization code - DO NOT EDIT


% --- Executes just before rootgui is made visible.
function rootgui_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to rootgui (see VARARGIN)

% Choose default command line output for rootgui
set(handles.axes2,'Visible','off');

h = handles.axes1;
set(h, 'Units', 'Normalized');
set(h,'OuterPosition',[.5 .2 .5 .8]);
handles.output = hObject;


% Update handles structure
guidata(hObject, handles);
end

% UIWAIT makes rootgui wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = rootgui_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;
end


% --- Executes on selection change in methods.
function methods_Callback(hObject, eventdata, handles)
% hObject    handle to methods (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns methods contents as cell array
%        contents{get(hObject,'Value')} returns selected item from methods
global val;
global guesses;
val = get(handles.methods,'Value');
if (val == 1)
     guesses = twoguesses();
elseif (val == 2)    
    guesses = twoguesses();
elseif(val == 3)
    guesses = oneguess();
elseif(val == 4)
    guesses = oneguess();    
elseif(val == 5)
    guesses = twoguesses();
elseif(val == 6)
    guesses = oneguess();
end
end


% --- Executes during object creation, after setting all properties.
function methods_CreateFcn(hObject, eventdata, handles)
% hObject    handle to methods (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
end



function equation_Callback(hObject, eventdata, handles)
% hObject    handle to equation (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of equation as text
%        str2double(get(hObject,'String')) returns contents of equation as a double
end

% --- Executes during object creation, after setting all properties.
function equation_CreateFcn(hObject, eventdata, handles)
% hObject    handle to equation (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
end



function epilson_Callback(hObject, eventdata, handles)
% hObject    handle to epilson (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of epilson as text
%        str2double(get(hObject,'String')) returns contents of epilson as a double
end

% --- Executes during object creation, after setting all properties.
function epilson_CreateFcn(hObject, eventdata, handles)
% hObject    handle to epilson (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
end


function itterations_Callback(hObject, eventdata, handles)
% hObject    handle to itterations (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of itterations as text
%        str2double(get(hObject,'String')) returns contents of itterations as a double
end

% --- Executes during object creation, after setting all properties.
function itterations_CreateFcn(hObject, eventdata, handles)
% hObject    handle to itterations (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end

end

function firstguess_Callback(hObject, eventdata, handles)
% hObject    handle to firstguess (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of firstguess as text
%        str2double(get(hObject,'String')) returns contents of firstguess as a double
end

% --- Executes during object creation, after setting all properties.
function firstguess_CreateFcn(hObject, eventdata, handles)
% hObject    handle to firstguess (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
end

function secondguess_Callback(hObject, eventdata, handles)
% hObject    handle to secondguess (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of secondguess as text
%        str2double(get(hObject,'String')) returns contents of secondguess as a double
end

% --- Executes during object creation, after setting all properties.
function secondguess_CreateFcn(hObject, eventdata, handles)
% hObject    handle to secondguess (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
end

% --- Executes on button press in next.
function next_Callback(hObject, eventdata, handles)
% hObject    handle to next (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global fun;
global mydata;
global i;
global val;
if i< size(mydata,1)
i = i+1;
disp (mydata(i,1));
end
if (val == 1)
     drawBiFalse(fun,mydata,i);
elseif (val == 2)
    drawBiFalse(fun,mydata,i);
elseif(val == 3)
    drawFixed(fun,mydata,i);
elseif(val == 4)
    drawothers(fun,mydata,i);
elseif(val == 5)
    drawSecant(fun,mydata,i);
elseif(val == 6)
    drawothers(fun,mydata,i);
end

end

% --- Executes on button press in prev.
function prev_Callback(hObject, eventdata, handles)
% hObject    handle to prev (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global fun;
global mydata;
global i;
global val;
if i>1
i = i-1;
disp (mydata(i,1));
end
if (val == 1)
     drawBiFalse(fun,mydata,i);
elseif (val == 2)
    drawBiFalse(fun,mydata,i);
elseif(val == 3)
    drawFixed(fun,mydata,i);
elseif(val == 4)
    drawothers(fun,mydata,i);
elseif(val == 5)
    drawSecant(fun,mydata,i);
elseif(val == 6)
     drawothers(fun,mydata,i);
end
end

% --- Executes on button press in solve.
function solve_Callback(hObject, eventdata, handles)
% hObject    handle to solve (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(handles.axes2,'Visible','off');
set(handles.next,'Enable','on');
set(handles.prev,'Enable','on');
equ = get(handles.equation,'String');
eps = get(handles.epilson,'String');
itts = get(handles.itterations,'String');

h = handles.axes1;
set(h, 'Units', 'Normalized');
set(h,'OuterPosition',[.5 .2 .5 .8]);
axes(handles.axes1);

    h2 = zoom;
    h2.Motion = 'both';
    h2.Enable = 'on';

global guesses;
global val;
global fun;
global mydata;
global i;
i = 1;
str2 = [];
forfixed = [];
testingconv = 0;
if (val == 1)
    try
     [f,xr,arr,eas,kitts,timeelapsed] = Bisection(equ,itts,eps,guesses{1},guesses{2});
    catch
        msgbox('root can not be detected','WARNING','warn');
        return;
    end
     str2 = print2guesses(arr,eas);
     testingconv = kitts;
     drawBiFalse(f,arr,i);
     
elseif (val == 2)
    try
    [f,xr,arr,eas,timeelapsed] = falseposition(equ,itts,eps,guesses{1},guesses{2});
    catch
       msgbox('root can not be detected','WARNING','warn');
       return;
    end
    str2 = print2guesses(arr,eas);
     drawBiFalse(f,arr,i);
elseif(val == 3)
    try
    [f,xr,arr,eas,testconv,timeelapsed] = FixedPoint(equ,itts,eps,guesses{1});
    catch
       msgbox('root must have (x)','WARNING','warn');
       return;
    end
    forfixed = testfixed(testconv);
    str2 = print1guess(arr,eas);
    testingconv = testconv;
    drawFixed(f,arr,i);
elseif(val == 4)
    try
    [f,xr,arr,eas,testconv,timeelapsed] = Newton(equ,itts,eps,guesses{1});
    catch
        return;
    end
    str2 = print1guess(arr,eas);
    testingconv = testconv;
    drawothers(f,arr,i);
elseif(val == 5)
    try
    [f,xr,arr,eas,timeelapsed] = Secant(equ,itts,eps,guesses{1},guesses{2}); 
    catch
        return;
    end
    str2 = print2guesses(arr,eas);
    drawSecant(f,arr,i);
elseif(val == 6)
    try
    [f,xr,sizerow,arr,all,eas,timeelapsed] = BirgeVeta(equ,itts,eps,guesses{1});
    catch
        return;
    end
    str2 = printbirge(arr,all,eas,sizerow);
    drawothers(f,arr,i);
elseif (val==7)
    try
    [root]= general_method(equ);
    catch
        return;
    end
    disp(root);
    gen = string();
    for j = 1:size(root,1)
    gen(j,1) = {sprintf('root : %f ',root(j,1))};
    end
    set(handles.answers,'String',gen);
    return;
end
fun = f;
mydata = arr;
str = printinlist(xr,size(arr,1),timeelapsed,eas(size(eas,1),1),testingconv,forfixed);
str = [str;str2];
writegui(str);
set(handles.answers,'String',str);
end  

function str = testfixed(testconv)
   if abs(testconv) <1
       str1 = "converge";
   else
       str1 = "diverge";
   end
   
   if testconv > 0
       str2 = ", monotonic";
   else
       str2 = ", oscillate";
   end
   str = strcat(str1,str2);
end

function str = printinlist(xr,sizearr,time,err,testconv,forfixed)
    str(1,1) = {sprintf('root : %f ',xr)};
    str(2,1) = {sprintf('number of itterations :  %d',sizearr)};
    str(3,1) = {sprintf('timeelapsed : %f \n',time)};
    str(4,1) = {sprintf('precision : %f \n',err)};
if(testconv == 0)
    str(5,1) = {sprintf('              **itterations** ')};
else
    str(5,1) = {sprintf('bound of error : %f',testconv)};
    if ~isempty(forfixed)
        str(6,1) = {sprintf(testfixed(testconv))};
        str(7,1) = {sprintf('              **itterations** ')};
    else
        str(6,1) = {sprintf('              **itterations** ')};
    end    
    
end

end


function root_Callback(hObject, eventdata, handles)
% hObject    handle to root (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of root as text
%        str2double(get(hObject,'String')) returns contents of root as a double
end

% --- Executes during object creation, after setting all properties.
function root_CreateFcn(hObject, eventdata, handles)
% hObject    handle to root (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
end

% --- Executes on selection change in answers.
function answers_Callback(hObject, eventdata, handles)
% hObject    handle to answers (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns answers contents as cell array
%        contents{get(hObject,'Value')} returns selected item from answers
end

% --- Executes during object creation, after setting all properties.
function answers_CreateFcn(hObject, eventdata, handles)
% hObject    handle to answers (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: listbox controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end
end


% --- Executes when user attempts to close figure1.
function figure1_CloseRequestFcn(hObject, eventdata, handles)
% hObject    handle to figure1 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hint: delete(hObject) closes the figure
global val;
val =1;
delete(hObject);
end
function answer = twoguesses()
  prompt = {'First Guess: ','Second Guess: '};
  dlgtitle = 'Guesses';
  dims = [1 38];
  definput = {' ',' '};
  answer = inputdlg(prompt,dlgtitle,dims,definput);  
end

function answer = oneguess()
  prompt = {'Intial Guess: '};
  dlgtitle = 'Guesses';
  dims = [1 38];
  definput = {' '};
  answer = inputdlg(prompt,dlgtitle,dims,definput);  
end

% --- Executes on button press in allmethods.
function allmethods_Callback(hObject, eventdata, handles)
% hObject    handle to allmethods (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(handles.axes2,'Visible','on');
h = handles.axes1;
set(h, 'Units', 'Normalized');
set(h,'OuterPosition',[.486 .46 .5 .55]);
set(handles.next,'Enable','off');
set(handles.prev,'Enable','off');
equ = get(handles.equation,'String');
eps = get(handles.epilson,'String');
itts = get(handles.itterations,'String');
prompt = {'Bisection First Guess: ','Bisection Second Guess: ','False-position First Guess: ','False-Position Second Guess: ','Fixed point guess: ','Newton guess: ','Secant First guess: ','Secant Second guess: ','Birge guess: ' };
dlgtitle = 'Guesses';
dims = [1 38];
definput = {'0','3','0','3','0','0','0','0','0'};
answer = inputdlg(prompt,dlgtitle,dims,definput);
disp(answer);
str = [];
   try
   [f,xr,arr,eas,kitts,timeelapsed] = Bisection(equ,itts,eps,answer{1},answer{2});
    arrbisection = arr(1:size(arr,1),1:1); 
    errbisection = eas(2:size(eas,1),1:1);
    str1 = printinlist(xr,size(arr,1),timeelapsed,eas(size(eas,1),1),kitts,[]);
    str2 = print2guesses(arr,eas);
    str = ['               **BISECTION**';str1;str2];
    catch
   end
    
    try
    [f,xr,arr,eas,timeelapsed] = falseposition(equ,itts,eps,answer{3},answer{4});
    arrfalse = arr(1:size(arr,1),1:1); 
    errfalse = eas(2:size(eas,1),1:1);
    str1 = printinlist(xr,size(arr,1),timeelapsed,eas(size(eas,1),1),0,[]);
    str2 = print2guesses(arr,eas);
    str = [str;'         **FALSE-POSITION**';str1;str2];
    catch
    end
    
    try
    [f,xr,arr,eas,testconv,timeelapsed] = FixedPoint(equ,itts,eps,answer{5});
    arrfixed = arr(1:size(arr,1),1:1);
    errfixed = eas(2:size(eas,1),1:1);
    str1 = printinlist(xr,size(arr,1),timeelapsed,eas(size(eas,1),1),testconv,testfixed(testconv));
    str2 = print1guess(arr,eas);
    str = [str;'         **FIXED-POINT**';str1;str2];
    catch
       msgbox('THERE IS NO (X)','WARNING','warn'); 
    end
    
    try
    [f,xr,arr,eas,testconv,timeelapsed] = Newton(equ,itts,eps,answer{6});
    arrnewton = arr(1:size(arr,1),1:1); 
    errnewton = eas(2:size(eas,1),1:1);
    str1 = printinlist(xr,size(arr,1),timeelapsed,eas(size(eas,1),1),testconv,[]);
    str2 = print1guess(arr,eas);
    str = [str;'         **NEWTON**';str1;str2];
    catch
    end
    
    try
    [f,xr,arr,eas,timeelapsed] = Secant(equ,itts,eps,answer{7},answer{8});  
    arrsecant = arr(1:size(arr,1),1:1); 
    errsecant = eas(2:size(eas,1),1:1);
    str1 = printinlist(xr,size(arr,1),timeelapsed,eas(size(eas,1),1),0);
    str2 = print2guesses(arr,eas);
    str = [str;'        **SECANT**';str1;str2];
    catch
    end
    
    
    try
    [f,xr,sizerow,arr,all,eas,timeelapsed] = BirgeVeta(equ,itts,eps,answer{9});
    arrbirge = arr(1:size(arr,1),1:1); 
    errbirge = eas(2:size(eas,1),1:1);
    str1 = printinlist(xr,size(arr,1),timeelapsed,eas(size(eas,1),1),0,[]);
    str2 = printbirge(arr,all,eas,sizerow);
    str = [str;'        **BIRGE-VEITA**';str1;str2];
    catch
       msgbox('EQUATION IS NOT A POLYNOMIAL','WARNING','warn');  
    end
    
    writegui(str);
    set(handles.answers,'String',str);
    
    axes(handles.axes1);
    
    try
    plotall(arrbisection);
    hold on
    catch
    end
    
    try
    plotall(arrfalse);
    hold on
    catch
    end
    
    try
    plotall(arrfixed);
    hold on
    catch
    end
    
    try
    plotall(arrnewton);
    hold on
    catch
    end
    
    try
    plotall(arrsecant);
    hold on
    catch
    end
    
    try
    plotall(arrbirge);
    catch
    end
    grid on;
    legend('Bisection', 'False', 'Fixed','Newton', 'Secant', 'Birge', 'Location', 'northEast')
    hold off
    
    h = zoom;
    h.Motion = 'both';
    h.Enable = 'on';
    
     axes(handles.axes2);
   
    try 
    plotall(errbisection);
    hold on
    catch
    end
    
    try
    plotall(errfalse);
    hold on
    catch
    end
    
    try
    plotall(errfixed);
    hold on
    catch
    end
    
    try
    plotall(errnewton);
    hold on
    catch
    end
    
    try
    plotall(errsecant);
    hold on
    catch
    end
    
    try
    plotall(errbirge);
    catch
    end
    grid on;
    legend('Bisection', 'False', 'Fixed','Newton', 'Secant', 'Birge', 'Location', 'northEast')
    hold off
    h2 = zoom;
    h2.Motion = 'both';
    h2.Enable = 'on';
     
     
end


% --- Executes on button press in read.
function read_Callback(hObject, eventdata, handles)
% hObject    handle to read (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
[file,path] = uigetfile('*.txt');
if isequal(file,0)
    disp('cancel');
else
    disp(['user selected ',fullfile(path,file)]);
    write(fullfile(path,file));
end
end


% --- Executes on button press in pushbutton6.
function pushbutton6_Callback(hObject, eventdata, handles)
% hObject    handle to pushbutton6 (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(handles.equation,'String','');
set(handles.epilson,'String','');
set(handles.itterations,'String','');
set(handles.answers,'String','');
cla(handles.axes1,'reset');
cla(handles.axes2,'reset');
end
