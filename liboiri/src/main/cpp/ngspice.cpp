/*
 * Copyright (c) 2016 Oiri Project
 *
 * This software is distributed under an MIT-style license.
 * See LICENSE file for more information.
 */

#include <cstring>
#include "ngspice.h"
#include "log.h"

namespace oiri {
Ngspice::Ngspice() {

}

Ngspice &Ngspice::GetInstance() {
    static Ngspice ngspice;
    return ngspice;
}

void Ngspice::Initialize() {
    ngSpice_Init(&SendCharCallback, &SendStatCallback, &ControlledExitCallback,
                 &SendDataCallback, &SendInitDataCallback, BGThreadRunningCallback, this);
    Command("unset interactive");
    Command("set noaskquit");
    Command("set nomoremode");
}

bool Ngspice::Command(const std::string &command) {
    int result = ngSpice_Command(const_cast<char *>(command.c_str()));
    return 0 == result;
}

int Ngspice::SendCharCallback(char *text, int id, void *user) {
    Ngspice *s = reinterpret_cast<Ngspice *>(user);

    const char tagO[] = {'s', 't', 'd', 'o', 'u', 't', ' '};
    const char tagE[] = {'s', 't', 'd', 'e', 'r', 'r', ' '};

    if (0 == std::strncmp(text, tagO, sizeof(tagO))) {
        Log::V(text + sizeof(tagO));
    }
    else if (0 == std::strncmp(text, tagE, sizeof(tagE))) {
        Log::D(text + sizeof(tagE));
    }
    else {
        Log::I(text);
    }

    return 0;
}

int Ngspice::SendStatCallback(char *text, int id, void *user) {
    Ngspice *s = reinterpret_cast<Ngspice *>(user);
    Log::D(text);
    return 0;
}

int Ngspice::ControlledExitCallback(int status, bool immediate, bool exitUponQuit, int id,
                                    void *user) {
    Ngspice *s = reinterpret_cast<Ngspice *>(user);
    return 0;
}

int Ngspice::SendDataCallback(pvecvaluesall vectors, int n, int id, void *user) {
    Ngspice *s = reinterpret_cast<Ngspice *>(user);
    if (nullptr == vectors) {
        return -1;
    }
    for (int i = 0; i < vectors->veccount; i++) {
        pvecvalues v = vectors->vecsa[i];
        if ('t' == v->name[0]) {
            Log::V("time\t%e", v->creal);
        }
    }
    return 0;
}

int Ngspice::SendInitDataCallback(pvecinfoall vectors, int id, void *user) {
    Ngspice *s = reinterpret_cast<Ngspice *>(user);
    if (nullptr == vectors) {
        return -1;
    }
    s->mVecCount = vectors->veccount;
    return 0;
}

int Ngspice::BGThreadRunningCallback(bool isRunning, int id, void *user) {
    Ngspice *s = reinterpret_cast<Ngspice *>(user);
    // The first argument 'isRunning' seems to be flipped.
    Log::D(s->IsRunning() ? "Running" : "Not Running");
    return 0;
}


}
