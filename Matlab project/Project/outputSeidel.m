function outputSeidel(X,Error,Final,Iterations,method,equations,results,noOfEq,timeElapsed)
fileID = fopen('Output.txt','at');
fprintf(fileID,'%s\n',method);
for j = 1:size(equations)
    fprintf(fileID,'%s',equations(j) + ' = ' + results(j));
    fprintf(fileID,'\n');
end
fprintf(fileID,'\n');
asciiA = double('a');
fprintf(fileID,'%s\t     ','Iteration');
currChar = char(asciiA);
errorLabel = strcat(currChar,'Error');
for i = 2:noOfEq
    fprintf(fileID,'%s\t     %s\t     ',currChar,errorLabel);
    currAscii = asciiA +i-1;
    currChar = char(currAscii);
    errorLabel = strcat(currChar,'Error');
end
fprintf(fileID,'%s\t     %s\t     ',currChar,errorLabel);
fprintf(fileID,'\n');
for j = 1:Iterations
    fprintf(fileID,'%u\t        ',j);
    for l = 1:noOfEq
        fprintf(fileID,'%.4f\t   %.4f\t   ',X(l,j+1),Error(l,j));
    end
    fprintf(fileID,'\n');
end
fprintf(fileID,'%s\n','Final Answers');
fprintf(fileID,'%.4f\t   ',Final);
fprintf(fileID,'\n');
fprintf(fileID,'%s\n','Number of iterations');
fprintf(fileID,'%u\n',Iterations);
fprintf(fileID,'%s','Time taken: ');
fprintf(fileID,'%d %s\n',timeElapsed,'seconds');
fprintf(fileID,'---------------------------------------------------------------\n');
fclose(fileID);