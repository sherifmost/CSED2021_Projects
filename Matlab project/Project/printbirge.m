function str = printbirge(arr,all,eas,sizerow)
disp(eas);
str(1,1) = {sprintf('         xr                          error')};
j = 1;
  for i=1:size(arr,1)
       if i==1
            str(i+j,1) = {sprintf('%d   %e',i,arr(i,1))};
       else
            str(i+j,1) = {sprintf('%d    %e       %e',i,arr(i,1),eas(i,1))};
       end
        for k = j:(sizerow-1+j)
            str(i+1+k) = {sprintf('      %e       %e      %e',all(k,1),all(k,2),all(k,3))};
        end
        j = j + sizerow;
  end
end