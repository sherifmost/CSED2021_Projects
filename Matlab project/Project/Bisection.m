%parameters are in string form equation, no of itterations , epsilon,
%intial guesses.
%output is vector of function, root, array of all lower,upper,roots and
%relative errors
function [f,xr,arr,eas,kitts,timeelapsed] = Bisection(equ,itterations,es,xl,xu)
f = conv2fun(equ);
xr = 0;
ea = 0;
if isempty(itterations) % check if itterations isnot given
    itterations = '50';
end
if isempty(es)    % check if epilson isnot given
    es = '0.00001';
end  
itterations = str2double(itterations);  % convert values from string to double
es = str2double(es);
xl = str2double(xl);
xu = str2double(xu);
kitts = ceil((log10(xu-xl)-log10(es))/log10(2));
if f(xl)*f(xu) >= 0
    disp('root can not be detected');
   %msgbox('root can not be detected','WARNING','warn');
   return;
end
xrs = zeros(1,1); % construct the arrays
eas = zeros(1,1);
xls = zeros(1,1);
xus = zeros(1,1);
fxrs = zeros(1,1);
fxls = zeros(1,1);
tic;
for i = 1:itterations
    xr = (xl+xu)/2;   %function for calculating next root
    xls(i,1)=xl;
    xus(i,1)=xu;
    xrs(i,1) = xr;
    if i>1
        ea = abs( (xrs(i,1) - xrs(i-1,1))/xrs(i,1))*100; 
        eas(i,1) = ea;
    end
    check = f(xr) * f(xl);
    fxls(i,1) = f(xl);
    fxrs(i,1) = f(xr);
    if check >0  % check for the product of f(xr) and f(xl) is negative
        xl = xr;
    else          % check for the product of f(xr) and f(xl) is positive
        xu = xr;
    end
    
    if (ea < es && i>1) || f(xr) == es  % check for accepted error or xr is the exact root
        break;
    end
end
timeelapsed = toc;
arr = [xrs,xls,xus,fxls,fxrs];