% algorithm for the gauss- seidel iterative method.
%  takes a matrix containing the equations, a matrix containing the results
% a matrix containing the initial guess for each variable column wise
% ,the number of variables, the precision (default value 0.00001) and the 
% maximum number of iterations (default value 50).
% returns a matrix containing the value of each variable after each
% iteration row wise, a matrix containing the relative error after each
% iteration for each variable row wise, a matrix containig the final values
% and the total number of iterations.
% the matrix containing the values has its first column as the initial
% guess.
function[X,Error,Final,Iterations] = gaussSeidel(num,eq,res,initial,precision,maxIterations)
X = initial;
A = zeros(num,num);
for i = 1: num
     A(i,1 : num) = getcoefficients(char(eq(i)),num);
end
%a loop to make sure that the pivoting coefficients are not zeros
for i = 1:num
    if(A(i,i) ==0)
        for j=1:num
            if(A(j,i) ~= 0)
                temp = A(j,1:num);
                A(j,1:num) = A(i,1:num);
                A(i,1:num) = temp;
                temp = res(j,1);
                res(j,1) = res(i,1);
                res(i,1) = temp;
            end
        end
    end
end
            
% gauss seidel iterations claculating the maximum error each time to check
% the stopping precision.
for i = 1: maxIterations
    X = [X zeros(num,1)];
    max = 0;
    for k = 1:num
        X(k,i+1) = res(k);
    for j = 1: num
        if(j>k)
            X(k,i+1) = X(k,i+1) - A(k,j)*X(j,i);
        end
        if(j<k)
            X(k,i+1) = X(k,i+1) - A(k,j)* X(j,i+1);
        end
    end
    % we divide by the coefficient after calculating the summation to
    % decrease the round off error amount.
    X(k,i+1) = X(k,i+1) / A(k,k);
    Error(k,i) = abs(X(k,i+1) - X(k,i));
    if(Error(k,i) > max)
        max = Error(k,i);
    end
    end
    if(max<precision)
        break;
    end
end
if(i>maxIterations)
    Iterations = maxIterations;
    i = maxIterations;
else
Iterations = i;
end
Final = X(1:num,i+1);


