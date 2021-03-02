import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { ToneRow } from './models/ToneRow';
import { Matrix } from './models/Matrix';

interface ToneRowMap {
  [id: string]: ToneRow;
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
}
