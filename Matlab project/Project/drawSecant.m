function drawSecant(f,arr,i)
jlower = 2;
jupper = 3;
if arr(i,jlower) < arr(i,jupper)
    x = linspace(arr(i,jlower)-1,arr(i,jupper)+1,100);
else
    x = linspace(arr(i,jupper)-1,arr(i,jlower)+1,100);
end
y = zeros(size(x));
   for j=1:size(x,2)
     y(j) = f(x(j));  
   end
plot(x,y);
hold on

xlabel('x');
ylabel('f(x)');
A = [arr(i,jlower)  arr(i,jupper)];
B = [f(arr(i,jlower)) f(arr(i,jupper))];
xlim = get(gca,'XLim');
m = (B(2)-B(1))/(A(2)-A(1));
n = B(1)- A(1)*m; 
y1 = m*xlim(1) + n;
y2 = m*xlim(2) + n;
line([xlim(1) xlim(2)],[y1 y2],'Color','k','LineWidth',0.5);
line([arr(i,jlower),arr(i,jlower)],get(gca,'ylim'),'Color','k','LineWidth',1);
line([arr(i,jupper),arr(i,jupper)],get(gca,'ylim'));
ax = gca;
grid on;
ax.GridColor = 'k';
ax.XColor = 'w';
ax.YColor = 'w';
hold off
end