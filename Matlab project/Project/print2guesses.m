function str = print2guesses(arr,eas)
str(1,1) = {sprintf('              xr                 xl                   xu                    f(xl)                    f(xr)                 error')};
  for i=1:size(arr,1)
       if i==1
            str(i+1,1) = {sprintf('%d   %e  %e  %e    %e   %e',i,arr(i,1),arr(i,2),arr(i,3),arr(i,4),arr(i,5))};
        else
            str(i+1,1) = {sprintf('%d    %e   %e    %e    %e    %e    %e',i,arr(i,1),arr(i,2),arr(i,3),arr(i,4),arr(i,5),eas(i,1))};
        end
  end
end

