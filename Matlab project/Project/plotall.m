function plotall(arr)
    itt = 1:size(arr);
    plot(itt,arr);
    ax = gca;
    grid on;
    ax.GridColor = 'k'; 
    ax.XColor = 'w';
    ax.YColor = 'w';
end