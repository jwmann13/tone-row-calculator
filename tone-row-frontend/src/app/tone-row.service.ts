import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ToneRow } from './models/ToneRow';
import { Matrix } from './models/Matrix';

interface ToneRowMap {
  [id: string]: ToneRow;
}

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

export interface ToneRowMeta {
  toneRowId: number;
  work?: Work;
  composers?: Composer[];
}

@Injectable({
  providedIn: 'root'
})
export class ToneRowService {

  baseURL: string = "http://localhost:8080/api";
  httpProperties: object = {
    headers: new HttpHeaders({
      "Content-Type": "application/json"
    })
  }

  constructor(private http: HttpClient) { }

  getAllToneRows(): Observable<ToneRowMap | null> {
    return this.http.get<ToneRowMap | null>(this.baseURL + "/tonerow", this.httpProperties)
    .pipe(
      tap(x => x),
      catchError(err => {
        console.log(err);
        return of(null);
      })
    )
  }

  getToneRow(toneRowId: number): Observable<ToneRow | null> {
    return this.http.get<ToneRow>(this.baseURL + `/tonerow?id=${toneRowId}`, this.httpProperties)
    .pipe(
      tap(x => x),
      catchError(err => {
        console.log(err);
        return of(null);
      })
    )
  }

  getMatrix(toneRowId: number): Observable<Matrix | null> {
    return this.http.get<Matrix>(this.baseURL + `/matrix?id=${toneRowId}`, this.httpProperties)
    .pipe(
      tap(x => x),
      catchError(err => {
        console.log(err);
        return of(null);
      })
    )
  }

  postToneRow(newToneRow: object): Observable<ToneRow | null> {
    return this.http.post<ToneRow>(this.baseURL + "/tonerow", newToneRow, this.httpProperties);
  }

  getToneRowMeta(toneRowId: number): Observable<ToneRowMeta | null> {
    return this.http.get<ToneRowMeta | null>(this.baseURL + `/tonerow/meta?id=${toneRowId}`, this.httpProperties)
      .pipe(
        catchError(err => {
          console.log(err);
          return of(null);
        })
      )
  }

  postToneRowMeta(noteOrder: number[], composers: string[], work: string): Observable<ToneRowMeta | null> {
    return this.http.post<ToneRowMeta | null>(this.baseURL + "/tonerow/meta",
      { noteOrder, work, composers },
      this.httpProperties)
      .pipe(
        tap(x => console.log(x)),
        catchError(err => {
          console.log(err);
          return of(null);
        })
      )
  }
}
