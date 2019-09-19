//
// Created by s2s8tb on 2019/9/18.
//

#include <iostream>
using namespace std;
const int MAX = 3;
int main()
{
    int var[MAX] = {10,20,300};
    int *ptr;

    ptr = var;
    for (int i=0;i<MAX;i++)
    {
        cout << "Address of var[" << i << "] = ";
        cout << ptr << endl;

        cout << "Value of var[" << i << "] = ";
        cout << * ptr << endl;
        ptr ++;
    }
    return 0;
}