function drawothers(f,arr,i)
t = linspace(arr(i,1)-1,arr(i,1)+1,100);
y = zeros(size(t));
for j=1:size(t,2)
    y(j) = f(t(j));  
end
plot(t,y);
hold on
z = zeros(size(arr,1),1);
for k=1:size(arr,1)
    z(k,1) = f(arr(k,1));  
end
xlabel('x');
ylabel('f(x)');
syms x
dy = matlabFunction(diff(f,x));
k=i; 
line([arr(k,1),arr(k,1)],get(gca,'ylim'),'Color','k','LineWidth',1);
tang=(t-arr(k,1))*dy(arr(k,1))+z(k,1);
plot(t,tang);
ax = gca;
grid on;
ax.GridColor = 'k';
ax.XColor = 'w';
ax.YColor = 'w';
hold off

end