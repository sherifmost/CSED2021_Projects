function varargout = GUI(varargin)
% GUI MATLAB code for GUI.fig
%      GUI, by itself, creates a new GUI or raises the existing
%      singleton*.
%
%      H = GUI returns the handle to a new GUI or the handle to
%      the existing singleton*.
%
%      GUI('CALLBACK',hObject,eventData,handles,...) calls the local
%      function named CALLBACK in GUI.M with the given input arguments.
%
%      GUI('Property','Value',...) creates a new GUI or raises the
%      existing singleton*.  Starting from the left, property value pairs are
%      applied to the GUI before GUI_OpeningFcn gets called.  An
%      unrecognized property name or invalid value makes property application
%      stop.  All inputs are passed to GUI_OpeningFcn via varargin.
%
%      *See GUI Options on GUIDE's Tools menu.  Choose "GUI allows only one
%      instance to run (singleton)".
%
% See also: GUIDE, GUIDATA, GUIHANDLES

% Edit the above text to modify the response to help GUI

% Last Modified by GUIDE v2.5 07-May-2019 13:48:52

% Begin initialization code - DO NOT EDIT
gui_Singleton = 1;
gui_State = struct('gui_Name',       mfilename, ...
                   'gui_Singleton',  gui_Singleton, ...
                   'gui_OpeningFcn', @GUI_OpeningFcn, ...
                   'gui_OutputFcn',  @GUI_OutputFcn, ...
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
% End initialization code - DO NOT EDIT


% --- Executes just before GUI is made visible.
function GUI_OpeningFcn(hObject, eventdata, handles, varargin)
% This function has no output args, see OutputFcn.
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
% varargin   command line arguments to GUI (see VARARGIN)
global i;
i = 1;
global results;
global noOfEq;
global method;
global equations;
global initialGuesses;
set(handles.txt_equation,'enable','off');
set(handles.btn_addEquation,'enable','off');
set(handles.txt_initialGuess,'enable','off');
set(handles.btn_addInitialGuess,'enable','off');
set(handles.txt_maxIterations,'enable','off');
set(handles.txt_precision,'enable','off');
set(handles.btn_done,'enable','off');

fileID = fopen('Output.txt','w');
fclose(fileID);

% Choose default command line output for GUI
handles.output = hObject;

% Update handles structure
guidata(hObject, handles);

% UIWAIT makes GUI wait for user response (see UIRESUME)
% uiwait(handles.figure1);


% --- Outputs from this function are returned to the command line.
function varargout = GUI_OutputFcn(hObject, eventdata, handles) 
% varargout  cell array for returning output args (see VARARGOUT);
% hObject    handle to figure
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Get default command line output from handles structure
varargout{1} = handles.output;


% --- Executes on selection change in menu_methods.
function menu_methods_Callback(hObject, eventdata, handles)
% hObject    handle to menu_methods (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns menu_methods contents as cell array
%        contents{get(hObject,'Value')} returns selected item from menu_methods


% --- Executes during object creation, after setting all properties.
function menu_methods_CreateFcn(hObject, eventdata, handles)
% hObject    handle to menu_methods (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: popupmenu controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function txt_noOfEq_Callback(hObject, eventdata, handles)
% hObject    handle to txt_noOfEq (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of txt_noOfEq as text
%        str2double(get(hObject,'String')) returns contents of txt_noOfEq as a double


% --- Executes during object creation, after setting all properties.
function txt_noOfEq_CreateFcn(hObject, eventdata, handles)
% hObject    handle to txt_noOfEq (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in btn_go.
function btn_go_Callback(hObject, eventdata, handles)
% hObject    handle to btn_go (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global method;
global noOfEq;
global results;
noOfEq = str2num(get(handles.txt_noOfEq,'String'));
results = zeros(1,noOfEq);
methods = get(handles.menu_methods,'String');
methodVal = get(handles.menu_methods,'Value');
method = methods{methodVal};
set(handles.txt_equation,'enable','on');
set(handles.btn_addEquation,'enable','on');
if methodVal == 4 || methodVal == 5
    set(handles.txt_initialGuess,'enable','on');
    set(handles.btn_addInitialGuess,'enable','on');
    set(handles.txt_maxIterations,'enable','on');
    set(handles.txt_precision,'enable','on');
    set(handles.btn_done,'enable','on');
end



function txt_equation_Callback(hObject, eventdata, handles)
% hObject    handle to txt_equation (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of txt_equation as text
%        str2double(get(hObject,'String')) returns contents of txt_equation as a double


% --- Executes during object creation, after setting all properties.
function txt_equation_CreateFcn(hObject, eventdata, handles)
% hObject    handle to txt_equation (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in btn_addEquation.
function btn_addEquation_Callback(hObject, eventdata, handles)
% hObject    handle to btn_addEquation (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global i;
global equations;
global noOfEq;
global results;
global method;
eqn = getResultFromEqn(string(get(handles.txt_equation,'String')));
methodVal = get(handles.menu_methods,'Value');
if (i == 1)
    equations = eqn;
else
    equations = [equations;eqn];
end
if i == noOfEq
    set(handles.btn_addEquation,'enable','off');
    set(handles.txt_equation,'enable','off');
    i = 1;
    if methodVal == 1
        tic;
        [M,X,delta] = gaussianElemination(noOfEq,equations,results);
        timeElapsed = toc;
        outputGaussAndLU(M,X,delta,method,equations,results,noOfEq,timeElapsed);
        showOutputInTxt(handles);
        open('Output.txt');
    elseif methodVal == 2
        tic;
        [LU,X,delta] = LUDecomposition(noOfEq,equations,results);
        timeElapsed = toc;
        outputGaussAndLU(LU,X,delta,method,equations,results,noOfEq,timeElapsed);
        showOutputInTxt(handles);
        open('Output.txt');
    elseif methodVal == 3
        tic;
        [X,flag] = gaussJordan(noOfEq,equations,results);
        timeElapsed = toc;
        outputJordan(X,flag,method,equations,results,noOfEq,timeElapsed);
        showOutputInTxt(handles);
        open('Output.txt');
    elseif methodVal == 5
        tic;
        [M,X,delta] = gaussianElemination(noOfEq,equations,results);
        timeElapsed = toc;
        outputGaussAndLU(M,X,delta,'Gaussian-Elemination',equations,results,noOfEq,timeElapsed);
        tic;
        [LU,X,delta] = LUDecomposition(noOfEq,equations,results);
        timeElapsed = toc;
        outputGaussAndLU(LU,X,delta,'LU Decomposition',equations,results,noOfEq,timeElapsed);
        tic;
        [X,flag] = gaussJordan(noOfEq,equations,results);
        timeElapsed = toc;
        outputJordan(X,flag,'Gauss Jordan',equations,results,noOfEq,timeElapsed);
        showOutputInTxt(handles);
        open('Output.txt');
    end
else
    set(handles.txt_equation,'String','');
    i = i + 1;
end

function eqn = getResultFromEqn(eq)
global results;
global i;
eqn = split(eq);
if ~isletter(eqn(end))
    res = eqn(end-1) + eqn(end);
    eqn = join(eqn);
    newEqn = "";
    for j = 1:strlength(eqn)-4
        newEqn = newEqn + eqn{1}(j);
    end
    res = -str2double(res);
    if i == 1
        results = res;
    else
        results = [results;res];
    end
    eqn = string(cellstr(newEqn));
    eqn = regexprep(eqn, '\s+', '');
else
    res = str2double(0);
    results = [results;res];
    eqn = string(cellstr(join(eqn)));
    eqn = regexprep(eqn, '\s+', '');
end




function txt_initialGuess_Callback(hObject, eventdata, handles)
% hObject    handle to txt_initialGuess (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of txt_initialGuess as text
%        str2double(get(hObject,'String')) returns contents of txt_initialGuess as a double


% --- Executes during object creation, after setting all properties.
function txt_initialGuess_CreateFcn(hObject, eventdata, handles)
% hObject    handle to txt_initialGuess (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in btn_addInitialGuess.
function btn_addInitialGuess_Callback(hObject, eventdata, handles)
% hObject    handle to btn_addInitialGuess (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global i;
global initialGuesses;
global noOfEq;
if (i == 1)
    initialGuesses = str2double(get(handles.txt_initialGuess,'String'));
else
    initialGuesses = [initialGuesses;str2double(get(handles.txt_initialGuess,'String'))];
end
if i == noOfEq
    set(handles.btn_addInitialGuess,'enable','off');
    set(handles.txt_initialGuess,'enable','off');
    i = 1;
else
    set(handles.txt_initialGuess,'String','');
    i = i + 1;
end



function txt_maxIterations_Callback(hObject, eventdata, handles)
% hObject    handle to txt_maxIterations (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of txt_maxIterations as text
%        str2double(get(hObject,'String')) returns contents of txt_maxIterations as a double


% --- Executes during object creation, after setting all properties.
function txt_maxIterations_CreateFcn(hObject, eventdata, handles)
% hObject    handle to txt_maxIterations (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end



function txt_precision_Callback(hObject, eventdata, handles)
% hObject    handle to txt_precision (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: get(hObject,'String') returns contents of txt_precision as text
%        str2double(get(hObject,'String')) returns contents of txt_precision as a double


% --- Executes during object creation, after setting all properties.
function txt_precision_CreateFcn(hObject, eventdata, handles)
% hObject    handle to txt_precision (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: edit controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in btn_done.
function btn_done_Callback(hObject, eventdata, handles)
% hObject    handle to btn_done (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
global equations;
global results;
global initialGuesses;
global noOfEq;
global method;
precision = 0.00001;
max_Iterations = 50;
S = get(handles.txt_precision,'String');
if ~isempty(S)
    precision = str2num(S);
end
S = get(handles.txt_maxIterations,'String');
if ~isempty(S)
    max_Iterations = str2double(S);
end
methodVal = get(handles.menu_methods,'Value');
if methodVal == 4 || methodVal == 5
    tic;
    [X,Error,Final,Iterations] = gaussSeidel(noOfEq,equations,results,initialGuesses,precision,max_Iterations);
    timeElapsed = toc;
    plotGaussSeidel(X,Iterations,handles);
    outputSeidel(X,Error,Final,Iterations,'Gauss-Seidel',equations,results,noOfEq,timeElapsed);
    showOutputInTxt(handles);
    open('Output.txt');
end



% --- Executes on button press in btn_fileChooser.
function btn_fileChooser_Callback(hObject, eventdata, handles)
% hObject    handle to btn_fileChooser (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
fileName = uigetfile('*.txt');
set(handles.lbl_fileName,'String',fileName);
fileReader('',fileName,'',handles);
open('Output.txt');
showOutputInTxt(handles);

function showOutputInTxt(handles)
set(handles.lbx_output, 'Max', 2); %// Enable multi-line string input to the editbox
fid=fopen('Output.txt');
tline = fgetl(fid);
tlines = cell(0,1);
while ischar(tline)
    tlines{end+1,1} = tline;
    tline = fgetl(fid);
end
fclose(fid);
set(handles.lbx_output,'String',tlines);


% --- Executes on selection change in lbx_output.
function lbx_output_Callback(hObject, eventdata, handles)
% hObject    handle to lbx_output (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)

% Hints: contents = cellstr(get(hObject,'String')) returns lbx_output contents as cell array
%        contents{get(hObject,'Value')} returns selected item from lbx_output


% --- Executes during object creation, after setting all properties.
function lbx_output_CreateFcn(hObject, eventdata, handles)
% hObject    handle to lbx_output (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    empty - handles not created until after all CreateFcns called

% Hint: listbox controls usually have a white background on Windows.
%       See ISPC and COMPUTER.
if ispc && isequal(get(hObject,'BackgroundColor'), get(0,'defaultUicontrolBackgroundColor'))
    set(hObject,'BackgroundColor','white');
end


% --- Executes on button press in btn_clearAll.
function btn_clearAll_Callback(hObject, eventdata, handles)
% hObject    handle to btn_clearAll (see GCBO)
% eventdata  reserved - to be defined in a future version of MATLAB
% handles    structure with handles and user data (see GUIDATA)
set(handles.txt_equation, 'String', '');
set(handles.txt_equation,'enable','off');
set(handles.btn_addEquation,'enable','off');
set(handles.txt_initialGuess, 'String', '');
set(handles.txt_initialGuess,'enable','off');
set(handles.btn_addInitialGuess,'enable','off');
set(handles.txt_maxIterations, 'String', '');
set(handles.txt_maxIterations,'enable','off');
set(handles.txt_precision, 'String', '');
set(handles.txt_precision,'enable','off');
set(handles.btn_done,'enable','off');
