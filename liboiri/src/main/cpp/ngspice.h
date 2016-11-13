/*
 * Copyright (c) 2016 Oiri Project
 *
 * This software is distributed under an MIT-style license.
 * See LICENSE file for more information.
 */

#ifndef OIRI_NGSPICE_H
#define OIRI_NGSPICE_H

#include <string>
#include <ngspice/sharedspice.h>

namespace oiri {


class Ngspice {
private:
    int mVecCount;

private:
    Ngspice();

    ~Ngspice() = default;

public:
    Ngspice(const Ngspice &) = delete;

    Ngspice &operator=(const Ngspice &) = delete;

    Ngspice(Ngspice &&) = delete;

    Ngspice &operator=(Ngspice &&) = delete;

    static Ngspice &GetInstance();

    void Initialize();

    inline void Reset() {
        mVecCount = 0;
        Command("reset");
    }

    inline bool Run() {
        return Command("bg_run");
    }

    inline bool Halt() {
        return Command("bg_halt");
    }

    inline bool IsRunning() {
        return ngSpice_running();
    }

    inline bool SetBrakePoint(double time) {
        return ngSpice_SetBkpt(time);
    }

    bool Command(const std::string &command);

private:
    static int SendCharCallback(char *text, int id, void *user);

    static int SendStatCallback(char *text, int id, void *user);

    static int ControlledExitCallback(int status, bool immediate, bool exitUponQuit, int id,
                                      void *user);

    static int SendDataCallback(pvecvaluesall vectors, int n, int id, void *user);

    static int SendInitDataCallback(pvecinfoall vectors, int id, void *user);

    static int BGThreadRunningCallback(bool isRunning, int id, void *user);
};

}
#endif //OIRI_NGSPICE_H
