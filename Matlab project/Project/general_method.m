%dekker's method
function[roots]= general_method(equ)
f=conv2fun(equ);
roots=zeros(1,1);
xl=-50;
xu=-49;
xr=0;
n=1;
itterations=100;
es=0.000001;
while xu<50
    if f(xl)*f(xu)>0
        xl=xl+1;
        xu=xu+1;
        continue;
    end
    xutemp=xu;
    for i=1:itterations
        if abs(f(xl))<abs(f(xu))
            temp=xl;
            xl=xu;
            xu=temp;
        end
        m=(xl+xu)/2;
        s= xu - (f(xu)*(xu-xl))/(f(xu)-f(xl));
        if ((s>xu&&s<xl)&&(xu<xl))||((s>xl&&s<xu)&&(xl<xu))
            xr=s;
        else
            xr=m;
        end
        if f(xl)*f(xr)<0
            xu=xr;
        else
            xl=xu;
            xu=xr;
        end
        ea=xu-xl;
        if abs(ea)<es || f(xr)==0
            break;
        end
    end
    roots(n,1)=xr;
    n=n+1;
    xl=xutemp+0.01;
    xu=xl+1;
end
if(n==1)
    msgbox('root can not be detected','WARNING','warn');
    return;
end