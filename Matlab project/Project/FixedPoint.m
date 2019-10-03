function [f,xr,arr,eas,testconv,timeelapsed] = FixedPoint(equ,itterations,es,x0)
numb = coeffFixed(equ);
f=conv2fun(equ);
ftemp = @(x) numb*x;
f = @(x) (f(x)+ftemp(x))/numb; 
ea = 0;
if isempty(itterations) % check if itterations isnot given
    itterations = '50';
end
if isempty(es)    % check if epilson isnot given
    es = '0.00001';
end 
itterations = str2double(itterations);  % convert values from string to double
es = str2double(es);
xi = str2double(x0);
arr = zeros(1,1); % construct the arrays
%arr(1,1)=xi;
gs = zeros(1,1);
eas = zeros(1,1);
tic;
for i=1 :itterations
    arr(i,1)=xi;
    gs(i,1) = f(xi);
    if i>1
        ea= abs((arr(i,1)-arr(i-1,1))/arr(i,1))*100;  
        eas(i,1)=ea;
    end
    if((ea<es && i>1) || f(xi)==xi)  
        break;
    end
    xi=f(xi);
    if(xi==inf||xi==-inf)
        break;
    end
end
timeelapsed = toc;
xr = xi;
if xr ~= arr(size(arr,1),1)
eas(size(eas,1)+1,1) = abs( (xr - arr(size(arr,1),1))/xr)*100;
end
syms x
df =matlabFunction( diff(f,x));
testconv = df(xi);
arr = [arr,gs];
disp(arr);
end