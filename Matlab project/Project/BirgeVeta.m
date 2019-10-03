function[f,xr,sizerow,arr,all,eas,timeelapsed] = BirgeVeta(equ,itterations,es,xi)
f = conv2fun(equ);
ea = 0;
if isempty(itterations) % check if itterations isnot given
    itterations = '50';
end
if isempty(es)    % check if epilson isnot given
    es = '0.00001';
end  
itterations = str2double(itterations);  % convert values from string to double
es = str2double(es);
xi = str2double(xi);
arr = zeros(1,1);
eas = zeros(1,1);
all = [];
temp = [];
A = polycoeffcients(equ);
sizerow = size(A,1);
B = zeros(sizerow,1);
B(1,1) = A(1,1);
C = zeros(sizerow,1);
C(1,1) = A(1,1);
tic;
for i=1:itterations
   % xr = xi;
    arr(i,1)= xi;
    for j=2:sizerow
        B(j,1) = B(j-1,1) * xi + A(j,1);
        if j<sizerow
            C(j,1) = C(j-1,1) * xi + B(j,1);
        end
    end
    temparr = [A,B,C];
    all = [temp;temparr];
    temp = all;
   % all = [all;temparr];
     if i>1
        ea = abs( (arr(i,1) - arr(i-1,1))/arr(i,1))*100; 
        eas(i,1) = ea;
     end
    if (ea < es && i>1) || f(xi) == 0  % check for accepted error or xr is the exact root
        break;
    end
     xi = xi - B(sizerow,1) / C(sizerow-1,1);
     if(xi==inf||xi==-inf)
        break;
    end
end
xr = xi;
if xr ~= arr(size(arr,1),1)
eas(size(eas,1)+1,1) = abs( (xr - arr(size(arr,1),1))/xr)*100;
end
timeelapsed = toc;
