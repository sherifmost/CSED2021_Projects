function drawBiFalse(f,arr,i)
colsize = size(arr,2);
jroot = 1;
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
xlabel('x');
ylabel('f(x)');
ax = gca;
grid on;
ax.GridColor = 'k';
ax.XColor = 'w';
ax.YColor = 'w';
line([arr(i,jlower),arr(i,jlower)],get(gca,'ylim'),'Color','k','LineWidth',1);
line([arr(i,jroot),arr(i,jroot)],get(gca,'ylim'),'Color','c','LineWidth',1);
line([arr(i,jupper),arr(i,jupper)],get(gca,'ylim'));
end