function [f,xr,arr,eas,testconv,timeelapsed] = Newton(equ,itterations,es,x0)
f=conv2fun(equ);
syms x
df =matlabFunction( diff(f,x));
fn=@(x) x - f(x)/df(x);
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
eas = zeros(1,1);
ff = zeros(1,1);
deriv = zeros(1,1);
%arr(1,1) = xi;
tic;
for i=1 :itterations
    disp(xi);
    arr(i,1)=xi;
    ff(i,1) = f(xi);
    deriv(i,1) = df(xi);
    if i>1
        ea= abs((arr(i,1)-arr(i-1,1))/arr(i,1))*100;
        eas(i,1)=ea;
    end
    
    if (ea<es && i>1) || f(xi)==es
        break;
    end  
    if df(xi)==0
         msgbox('Division by zero','WARNING','warn');
        return;
    end
    xi=fn(xi);
    if(xi==inf||xi==-inf)
        break;
    end
end
timeelapsed =toc;
xr = xi;
if xr ~= arr(size(arr,1),1)
eas(size(eas,1)+1,1) = abs( (xr - arr(size(arr,1),1))/xr)*100;
end
syms x
ddf = matlabFunction(diff(df,x));
testconv = abs(ddf(xr)/(2*df(xr)));
arr = [arr,ff,deriv];