function [f,xr,arr,eas,timeelapsed] = Secant(equ,itterations,es,x0,x1)
f=conv2fun(equ);
ea = 0;
if isempty(itterations) % check if itterations isnot given
    itterations = '50';
end
if isempty(es)    % check if epilson isnot given
    es = '0.00001';
end 
itterations = str2double(itterations);  % convert values from string to double
es = str2double(es);
xi0= str2double(x0);
xi1= str2double(x1);
xi0s = zeros(1,1);
xi1s = zeros(1,1);
xrs = zeros(1,1);
fxi0s = zeros(1,1);
fxi1s = zeros(1,1);
arr = zeros(1,1); % construct the arrays
eas = zeros(1,1);
tic;
for i=1 :itterations
    xi0s(i,1) =  xi0;
    xi1s(i,1) = xi1;
    fxi0s(i,1) = f(xi0);
    fxi1s(i,1) = f(xi1);
    if (f(xi0)-f(xi1))==0
        msgbox('Division by zero','WARNING','warn');
        return;
    end
    xi=xi1-(f(xi1)*(xi0-xi1)/(f(xi0)-f(xi1)));
    xrs(i,1) = xi;
    xi0=xi1;
    xi1=xi;
  %  arr(i,1)=xi;
    if i>1
        ea= abs((xrs(i,1)-xrs(i-1,1))/xrs(i,1))*100;
        eas(i,1)=ea;
    end
    if((ea<es && i>1) || f(xi)==0)
        break;
    end   
end
xr = xi;
timeelapsed = toc;
arr = [xrs,xi0s,xi1s,fxi0s,fxi1s];
end