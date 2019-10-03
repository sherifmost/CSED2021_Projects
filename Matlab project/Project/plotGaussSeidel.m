% a function for plotting the variables' values with the number of
% iterations for the gauss seidel method takes the values matrix
% and the number of iterations as input.
function plotGaussSeidel(X,Iterations,handles)
x = 0:Iterations;
dim = size(X);
num = dim(1);
axes(handles.axes_seidel);
plot(x,X(1,1:Iterations+1))
asciiA = double('a');
var(1) = "y = a";
hold on
title('Gauss seidel plot');
xlabel('iterations')
ylabel('value')
for i = 2:num
    currAscii = asciiA +i-1;
    currChar = char(currAscii);
    currLegend = strcat("y = ",currChar);
    var(i) = currLegend;
    plot(x,X(i,1:Iterations+1))
end
ax = gca;
grid on;
ax.GridColor = 'k'; 
ax.XColor = 'w';
ax.YColor = 'w';
legend(var,'Location','northeast')
hold off