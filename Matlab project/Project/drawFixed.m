function drawFixed(f,arr,i)
x = linspace(arr(i,1)-1,arr(i,1)+1,100);
linear = x;
plot(x,linear);
hold on
y = zeros(size(x));
   for j=1:size(x,2)
     y(j) = f(x(j));  
   end
plot(x,y);
line([arr(i,1),arr(i,1)],get(gca,'ylim'),'Color','k','LineWidth',1);
ax = gca;
grid on;
ax.GridColor = 'k';
ax.XColor = 'w';
ax.YColor = 'w';
hold off
end