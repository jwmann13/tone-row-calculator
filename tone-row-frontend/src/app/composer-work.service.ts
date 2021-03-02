import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

export interface Work {
  workId?: number;
  title: string;
}

export interface ComposerWork {
  workId: number;
  composerId: number;
}

export interface Composer {
  composerId?: number;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class ComposerWorkService {

  baseURL: string = "http://localhost:8080/api";
  httpProperties: object = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  }

  constructor(private http: HttpClient) { }

  getWorkById(workId: number): Observable<Work | null> {
    return this.http.get<Work | null>(this.baseURL + `/work?id=${workId}`, this.httpProperties)
    .pipe(
      tap(x => x),
      catchError(err => {
        console.log(err);
        return of(null);
      })
    );
  }

  getComposerWork(workId: number): Observable<ComposerWork[] | null> {
    return this.http.get<ComposerWork[] | null>(this.baseURL + `/composerwork?id=${workId}`, this.httpProperties)
    .pipe(
      tap(x => x),
      catchError(err => {
        console.log(err);
        return of(null);
      })
    );
  }

  getComposer(composerId: number): Observable<Composer | null> {
    return this.http.get<Composer | null>(this.baseURL + `/composer?id=${composerId}`, this.httpProperties)
    .pipe(
      tap(x => console.log(x)),
      catchError(err => {
        console.log(err);
        return of(null);
      })
    )
  }
}
