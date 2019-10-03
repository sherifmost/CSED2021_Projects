function str = print1guess(arr,eas)
if size(arr,2) == 2      %fixed
  str(1,1) = {sprintf('               xr                            g(x)                               error')};
  for i=1:size(arr,1)
       if i==1
            str(i+1,1) = {sprintf('%d            %e            %e',i,arr(i,1),arr(i,2))};
        else
            str(i+1,1) = {sprintf('%d            %e            %e           %e',i,arr(i,1),arr(i,2),eas(i,1))};
        end
  end 
    
else
    str(1,1) = {sprintf('                 xr                     f(x)                          df(x)                    error')};
  for i=1:size(arr,1)
       if i==1
            str(i+1,1) = {sprintf('%d         %e         %e          %e',i,arr(i,1),arr(i,2),arr(i,3))};
        else
            str(i+1,1) = {sprintf('%d         %e         %e          %e        %e',i,arr(i,1),arr(i,2),arr(i,3),eas(i,1))};
        end
  end
    
end

%str(1,1) = {sprintf('xr              error')};
%  for i=1:size(arr,1)
%       if i==1
%            str(i+1,1) = {sprintf('%f',arr(i,1))};
%        else
%            str(i+1,1) = {sprintf('%f \t %f',arr(i,1),eas(i,1))};
%        end
%  end
end