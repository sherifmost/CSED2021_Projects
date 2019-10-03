function write(inputfile)
A = read(inputfile);
method ="";
global equ;
global fID;
fID = fopen('Output.txt','w');
for i=1:size(A,1)
    if mod(i,4)==1
        equ = A(i,1);
        equ = equ{1};
    elseif mod(i,4)==2
        method = A(i,1);
        method = method{1};
    elseif mod(i,4)==3
        guesses = A(i,1);
        splitguesses(guesses{1});
    else
        limits = A(i,1);
       get_itt_es(limits{1});
       methodused(method);
    end
    
end
end

function splitguesses(str)
global firstg;
global secondg;
str = textscan(str,'%f');
str = str{1};
firstg = string(str(1,1));
if size(str,1)==2
    secondg = string(str(2,1));
end
end

function get_itt_es(str)
global itts;
global es;
temp = str2double(str);
if mod(temp,1) == 0
    itts = str;
    es = '';
else 
    es = str;
    itts = '';
end
end

function formatnoitts_time(sizearr,timeelapsed)
    global fID;
    formatitts = 'no of itterations %d \n';
    fprintf(fID,formatitts,sizearr);
    formattime = 'timeelapsed %f \n';
    fprintf(fID,formattime,timeelapsed);
    
end

function itterations(str)
    global fID;
    for i=1:size(str,1)
        formatdata = '%s \n';
        fprintf(fID,formatdata,str{i});
    end
end

function formatroot_precision(root,precision)
    global fID;
    formatroot = 'root %f \n';
    fprintf(fID,formatroot,root);
    formatprecision = 'precision %f \n';
    fprintf(fID,formatprecision,precision);
end

function boundcond (bound)
   global fID;
   formatbound = 'bound of error %f \n';
   fprintf(fID,formatbound,bound);
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

function methodused (str)
global equ;
global itts;
global es;
global firstg;
global secondg;

if str == "bisection"
    [f,xr,arr,eas,kitts,timeelapsed] = Bisection(equ,itts,es,firstg,secondg);
    str = print2guesses(arr,eas);
    boundcond(kitts);
elseif str == "false-position"
    [f,xr,arr,eas,timeelapsed] = falseposition(equ,itts,es,firstg,secondg);
    str = print2guesses(arr,eas);
elseif str == "fixed-point"
    [f,xr,arr,eas,testconv,timeelapsed] = FixedPoint(equ,itts,es,firstg);
     str = print1guess(arr,eas);
     str1 = testfixed(testconv);
     str = [str;str1];
    boundcond(testconv);
elseif str == "newton"
    [f,xr,arr,eas,testconv,timeelapsed] = Newton(equ,itts,es,firstg);
    str = print1guess(arr,eas);
    boundcond(testconv);
elseif str == "secant"
    [f,xr,arr,eas,timeelapsed] = Secant(equ,itts,es,firstg,secondg);
    str = print2guesses(arr,eas);
elseif str == "birge-veita"
    [f,xr,sizerow,arr,all,eas,timeelapsed] = BirgeVeta(equ,itts,es,firstg);
    str = printbirge(arr,all,eas,sizerow);    
end
    formatnoitts_time(size(arr,1),timeelapsed);
    itterations(str);
    formatroot_precision(xr,eas(size(eas,1),1));
end