function writegui(str)
fid = fopen('Output.txt','w');  
for i=1:size(str,1)
    formatdata = '%s \n';
    fprintf(fid,formatdata,str{i});
end
end